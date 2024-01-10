package com.MyRH.Services;

import com.MyRH.Exceptions.PaymentFailedException;
import com.MyRH.Exceptions.PaymentProcessingException;
import com.MyRH.Models.DTOs.CompanyDto;
import com.MyRH.Models.Entities.Company;
import com.MyRH.Models.Entities.Files;
import com.MyRH.Models.Entities.Plan;
import com.MyRH.Models.Mappers.CompanyMapper;
import com.MyRH.Repositories.CompanyRepository;
import com.MyRH.Repositories.FileRepository;
import com.MyRH.Repositories.PlanRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

@Service
public class CompanyService {

    CompanyRepository companyRepository;
    PlanRepository planRepository;
    CompanyMapper companyMapper;
    FileRepository  fileRepository;
    FileService fileService;
    StripeService stripeService;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyMapper  companyMapper, FileService fileService, FileRepository  fileRepository, PlanRepository planRepository, StripeService stripeService) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
        this.planRepository = planRepository;
        this.stripeService = stripeService;
    }

    public Company createCompany(CompanyDto companyDto) throws IOException {
        Company company = companyMapper.toEntity(companyDto);
        Files image = fileService.storeDataIntoFileSystem(companyDto.getImage());
        Plan plan = planRepository.findById(1L).orElse(null);
        company.setImage(image);
        company.setPlan(plan);
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

    public Company updatePlan(Long id, long planId, String cardToken) {
        Company company = companyRepository.findById(id).orElse(null);
        Plan plan = planRepository.findById(planId).orElse(null);

        if (company != null && plan != null) {
            try {
                Charge charge = stripeService.chargeCreditCard(plan.getPrice(), cardToken);
                if (charge.getPaid()) {
                    company.setPlan(plan);
                    return companyRepository.save(company);
                } else {
                    throw new PaymentFailedException("Payment failed. Please try again.");
                }
            } catch (StripeException e) {
                throw new PaymentProcessingException("Error processing payment.", e);
            } catch (PaymentFailedException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

}
