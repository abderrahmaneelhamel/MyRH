package com.MyRH.Controllers;

import com.MyRH.Models.DTOs.ApplicantDto;
import com.MyRH.Models.Entities.Applicant;
import com.MyRH.Services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {

    ApplicantService  applicantService;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping
    public ResponseEntity<List<Applicant>> getAllApplicants(){
        List<Applicant> applicants = applicantService.getAllApplicants();
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable("id") Long id) {
        Applicant applicant = applicantService.getApplicantById(id);
        return ResponseEntity.ok(applicant);
    }

    @PostMapping
    public ResponseEntity<Applicant> addApplicant(@RequestBody ApplicantDto Applicant) {
        Applicant newApplicant = applicantService.createApplicant(Applicant);
        return ResponseEntity.ok(newApplicant);
    }
}
