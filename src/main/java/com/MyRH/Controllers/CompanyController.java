package com.MyRH.Controllers;

import com.MyRH.Models.DTOs.CompanyDto;
import com.MyRH.Models.Entities.Company;
import com.MyRH.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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
    public ResponseEntity<Company> addCompany(@RequestBody CompanyDto company) throws IOException {
        System.out.println(company);
        Company newCompany = companyService.createCompany(company);
        return ResponseEntity.ok(newCompany);
    }
    @PostMapping("/addImage/{id}")
    public ResponseEntity<Company> addImage(@PathVariable("id") Long id,@RequestParam("image") MultipartFile image) throws IOException {
        Company newCompany = companyService.addImageCompany(image,id);
        return ResponseEntity.ok(newCompany);
    }

    @PostMapping("/authinticate")
    public ResponseEntity authinticateCompany(@RequestBody CompanyDto Company) {
        Company authinticatedCompany = companyService.AuthenticateCompany(Company);
        return ResponseEntity.ok(Objects.requireNonNullElse(authinticatedCompany, "Please enter correct email and password"));
    }
}
