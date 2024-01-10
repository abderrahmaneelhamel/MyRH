package com.MyRH.Controllers;

import com.MyRH.Exceptions.UpgradeplanException;
import com.MyRH.Models.DTOs.ApplicationDto;
import com.MyRH.Models.DTOs.JobDto;
import com.MyRH.Models.Entities.Application;
import com.MyRH.Models.Entities.Job;
import com.MyRH.Services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/job")
@CrossOrigin(origins = "**")
public class JobController {

    JobService  jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs(){
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") Long id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<List<Application>> getApplicationById(@PathVariable("id") Long id) {
        List<Application> applications = jobService.getApplicationById(id);
        System.out.println(applications);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/updateApplication")
    public ResponseEntity<List<Application>> updateApplication(@RequestBody ApplicationDto applicationDto){
        List<Application> application = jobService.updateApplication(applicationDto.getId(),applicationDto.getStatus());
        return ResponseEntity.ok(application);
    }
    @PostMapping("/updateJob")
    public ResponseEntity<List<Job>> updateJob(@RequestBody JobDto jobDto){
        List<Job> job = jobService.updateJob(jobDto.getId(),jobDto.getStatus());
        return ResponseEntity.ok(job);
    }
    @PostMapping
    public ResponseEntity addJob(@RequestBody JobDto job){
        try {
            Job newJob = jobService.createJob(job);
            return ResponseEntity.ok(newJob);
        } catch (UpgradeplanException ex) {
            return ResponseEntity.status(409).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An internal server error occurred: " + ex.getMessage());
        }
    }
}
