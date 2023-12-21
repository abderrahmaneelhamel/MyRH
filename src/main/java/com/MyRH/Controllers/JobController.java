package com.MyRH.Controllers;

import com.MyRH.Models.DTOs.JobDto;
import com.MyRH.Models.Entities.Job;
import com.MyRH.Services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/job")
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

    @PostMapping
    public ResponseEntity<Job> addJob(@RequestBody JobDto job) {
        System.out.println(job);
        Job newJob = jobService.createJob(job);
        return ResponseEntity.ok(newJob);
    }
}
