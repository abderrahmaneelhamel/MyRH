package com.MyRH.Services;

import com.MyRH.Models.DTOs.CompanyDto;
import com.MyRH.Models.Entities.Company;
import com.MyRH.Models.Entities.Files;
import com.MyRH.Models.Mappers.CompanyMapper;
import com.MyRH.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class CompanyService {

    CompanyRepository companyRepository;
    CompanyMapper companyMapper;

    FileService fileService;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,CompanyMapper  companyMapper,FileService fileService) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.fileService = fileService;
    }

    public Company createCompany(CompanyDto companyDto) throws IOException {
        Company company = companyMapper.toEntity(companyDto);
        String HashedPassword = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, company.getPassword().toCharArray());
//        Files image = fileService.storeFile(companyDto.getImage());
        company.setPassword(HashedPassword);
//        company.setImage(image);
        return companyRepository.save(company);
    }
    public Company addImageCompany(MultipartFile uploadedImage,Long id) throws IOException {
        Company company = companyRepository.findById(id).orElse(null);
        Files image = fileService.storeFile(uploadedImage);
        company.setImage(image);
        return companyRepository.save(company);
    }

    public Company AuthenticateCompany(CompanyDto companyDto) {
        Company company = companyRepository.findByEmail(companyDto.getEmail());
        if (company != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(companyDto.getPassword().toCharArray(), company.getPassword());
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
}
