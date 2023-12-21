package com.MyRH.Services;

import com.MyRH.Models.DTOs.CompanyDto;
import com.MyRH.Models.Entities.Company;
import com.MyRH.Models.Mappers.CompanyMapper;
import com.MyRH.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;


import java.util.List;

@Service
public class CompanyService {

    CompanyRepository companyRepository;
    CompanyMapper companyMapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,CompanyMapper  companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public Company createCompany(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        String HashedPassword = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, company.getPassword().toCharArray());
        company.setPassword(HashedPassword);
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
