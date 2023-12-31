package com.MyRH.Services;

import com.MyRH.Exceptions.UpgradeplanException;
import com.MyRH.Models.DTOs.JobDto;
import com.MyRH.Models.Entities.Application;
import com.MyRH.Models.Entities.Company;
import com.MyRH.Models.Entities.Job;
import com.MyRH.Models.Enums.Status;
import com.MyRH.Models.Mappers.JobMapper;
import com.MyRH.Repositories.ApplicationRepository;
import com.MyRH.Repositories.CompanyRepository;
import com.MyRH.Repositories.JobRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobService {

    JobRepository  jobRepository;
    ApplicationRepository applicationRepository;
    CompanyRepository companyRepository;
    JobMapper  jobMapper;

    @Autowired
    public JobService(JobRepository JobRepository,JobMapper  JobMapper,ApplicationRepository applicationRepository,CompanyRepository companyRepository) {
        this.jobRepository = JobRepository;
        this.jobMapper = JobMapper;
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
    }

    public Job createJob(JobDto jobDto) throws UpgradeplanException {
        Job job = jobMapper.toEntity(jobDto);
        Company company = this.companyRepository.findById(jobDto.getCompanyId()).orElse(null);
        if (company != null) {
            int count = jobRepository.findJobsByCompany(company);
            int allowedPosts = company.getPlan().getAllowedPosts();
            if ((count < allowedPosts && count >= 0) || allowedPosts == -1){
                return jobRepository.save(job);
            }else{
                throw new UpgradeplanException("You have reached the maximum number of jobs for this plan");
            }
        }else{
            return null;
        }
    }

    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    public Job getJobById(Long id){
        return jobRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Application> getApplicationById(Long id){
        return applicationRepository.findByJobId(id).orElse(null);
    }

    @Transactional
    public List<Application> updateApplication(Long id, Status status){
        Application application =  applicationRepository.findById(id).orElse(null);
        if(application != null){
            application.setStatus(status);
            applicationRepository.save(application);
            return applicationRepository.findAll();
        }else {
            return null;
        }
    }
}
