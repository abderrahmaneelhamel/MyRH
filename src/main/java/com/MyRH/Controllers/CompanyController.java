package com.MyRH.Controllers;

import com.MyRH.Models.DTOs.CompanyDto;
import com.MyRH.Models.Entities.Company;
import com.MyRH.Services.CompanyService;
import com.MyRH.Services.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "**", allowedHeaders = "*")
public class CompanyController {

    CompanyService  companyService;
    FileService  fileService;

    @Autowired
    public CompanyController(CompanyService companyService,FileService  fileService) {
        this.companyService = companyService;
        this.fileService = fileService;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Company> addCompany(@Valid @ModelAttribute CompanyDto company) throws IOException {
        Company newCompany = companyService.createCompany(company);
        return ResponseEntity.ok(newCompany);
    }

    @PostMapping("/authinticate")
    public ResponseEntity authinticateCompany(@RequestBody CompanyDto Company) {
        Company authinticatedCompany = companyService.AuthenticateCompany(Company);
        return ResponseEntity.ok(Objects.requireNonNullElse(authinticatedCompany, "Please enter correct email and password"));
    }
}
