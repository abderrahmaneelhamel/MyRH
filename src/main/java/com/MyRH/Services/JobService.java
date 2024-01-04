package com.MyRH.Services;

import com.MyRH.Models.DTOs.JobDto;
import com.MyRH.Models.Entities.Job;
import com.MyRH.Models.Mappers.JobMapper;
import com.MyRH.Repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobService {

    JobRepository  jobRepository;
    JobMapper  jobMapper;

    @Autowired
    public JobService(JobRepository JobRepository,JobMapper  JobMapper) {
        this.jobRepository = JobRepository;
        this.jobMapper = JobMapper;
    }

    public Job createJob(JobDto jobDto) {
        Job job = jobMapper.toEntity(jobDto);
        System.out.println(job+"--"+jobDto);
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    public Job getJobById(Long id){
        return jobRepository.findById(id).orElse(null);
    }
}
