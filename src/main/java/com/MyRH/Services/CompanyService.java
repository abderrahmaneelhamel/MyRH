package com.MyRH.Services;

import com.MyRH.Models.DTOs.CompanyDto;
import com.MyRH.Models.Entities.Company;
import com.MyRH.Models.Entities.Files;
import com.MyRH.Models.Entities.Plan;
import com.MyRH.Models.Mappers.CompanyMapper;
import com.MyRH.Repositories.CompanyRepository;
import com.MyRH.Repositories.FileRepository;
import com.MyRH.Repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class CompanyService {

    CompanyRepository companyRepository;
    PlanRepository planRepository;
    CompanyMapper companyMapper;

    FileRepository  fileRepository;

    FileService fileService;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,CompanyMapper  companyMapper,FileService fileService,FileRepository  fileRepository,PlanRepository planRepository) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
        this.planRepository = planRepository;
    }

    public Company createCompany(CompanyDto companyDto) throws IOException {
        Company company = companyMapper.toEntity(companyDto);
        Files image = fileService.storeDataIntoFileSystem(companyDto.getImage());
        company.setImage(image);
        String HashedPassword = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, company.getPassword().toCharArray());
        company.setPassword(HashedPassword);
        return companyRepository.save(company);
    }

    public Company AuthenticateCompany(String email,String password){
        Company company = companyRepository.findCompanyWithoutImage(email).orElse(null);
        if (company != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), company.getPassword());
            if (result.verified) {
                return company;
            }
        }
        return null;
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id){
        return companyRepository.findById(id).orElse(null);
    }

    public Company updatePlan(Long id, long planId) {
        Company company = companyRepository.findById(id).orElse(null);
        Plan plan = planRepository.findById(planId).orElse(null);
        if (company != null) {
            company.setPlan(plan);
            return companyRepository.save(company);
        } else {
            return null;
        }
    }
}
