package com.MyRH.Controllers;

import com.MyRH.Models.DTOs.CompanyDto;
import com.MyRH.Models.Entities.Company;
import com.MyRH.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    CompanyService  companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        List<Company> Companies = companyService.getAllCompanies();
        return ResponseEntity.ok(Companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") Long id) {
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<Company> addCompany(@RequestBody CompanyDto Company) {
        Company newCompany = companyService.createCompany(Company);
        return ResponseEntity.ok(newCompany);
    }

    @PostMapping("/authinticate")
    public ResponseEntity authinticateCompany(@RequestBody CompanyDto Company) {
        Company authinticatedCompany = companyService.AuthenticateCompany(Company);
        return ResponseEntity.ok(Objects.requireNonNullElse(authinticatedCompany, "Please enter correct email and password"));
    }
}
