package com.MyRH.Controllers;

import com.MyRH.Models.DTOs.ApplicantDto;
import com.MyRH.Models.DTOs.ApplicationDto;
import com.MyRH.Models.Entities.Applicant;
import com.MyRH.Models.Entities.Application;
import com.MyRH.Models.Entities.Company;
import com.MyRH.Services.ApplicantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applicant")
@CrossOrigin(origins = "**")
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Applicant> addApplicant(@ModelAttribute ApplicantDto Applicant) throws IOException {
        Applicant newApplicant = applicantService.createApplicant(Applicant);
        return ResponseEntity.ok(newApplicant);
    }

    @PostMapping("/apply")
    public ResponseEntity<Application> applyToJob(@RequestBody ApplicationDto application) {
        Application newApplication = applicantService.applyToJob(application);
        return ResponseEntity.ok(newApplication);
    }

    @PostMapping("/authinticate")
    public ResponseEntity authinticateApplicant(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        Applicant authinticatedApplicant = applicantService.AuthenticateApplicant(email,password);
        if (authinticatedApplicant != null) {
            return ResponseEntity.ok(authinticatedApplicant);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
    @GetMapping("logout/{id}")
    public ResponseEntity logout(@PathVariable("id") Long id) {
        applicantService.logout(id);
        return ResponseEntity.status(HttpStatus.OK).body("Logout successful");
    }
    @GetMapping("/applications/{id}")
    public ResponseEntity<List<Application>> getApplicationById(@PathVariable("id") Long id) {
        List<Application> applications = applicantService.getApplicationsById(id);
        System.out.println(applications);
        return ResponseEntity.ok(applications);
    }
}
