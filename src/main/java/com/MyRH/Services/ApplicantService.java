package com.MyRH.Services;

import com.MyRH.Models.DTOs.ApplicantDto;
import com.MyRH.Models.DTOs.ApplicationDto;
import com.MyRH.Models.Entities.Applicant;
import com.MyRH.Models.Entities.Application;
import com.MyRH.Models.Entities.Job;
import com.MyRH.Models.Mappers.ApplicantMapper;
import com.MyRH.Models.Mappers.ApplicationMapper;
import com.MyRH.Repositories.ApplicantRepository;
import com.MyRH.Repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicantService {

    ApplicantRepository  applicantRepository;
    ApplicationRepository applicationRepository;
    JobService jobService;
    ApplicantMapper  applicantMapper;
    ApplicationMapper applicationMapper;


    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository,ApplicantMapper  applicantMapper,ApplicationMapper applicationMapper,ApplicationRepository applicationRepository,JobService jobService) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
        this.applicationMapper = applicationMapper;
        this.applicationRepository = applicationRepository;
        this.jobService = jobService;
    }

    public Applicant createApplicant(ApplicantDto applicantDto) {
        Applicant applicant = applicantMapper.toEntity(applicantDto);
        return applicantRepository.save(applicant);
    }

    public List<Applicant> getAllApplicants(){
        return applicantRepository.findAll();
    }

    public Application applyToJob(ApplicationDto applicationDto){
        Application application = applicationMapper.toEntity(applicationDto);
        application.setApplicant(this.getApplicantById(applicationDto.getApplicant_id()));
        application.setJob(jobService.getJobById(applicationDto.getJob_id()));
        return applicationRepository.save(application);
    }


    public Applicant getApplicantById(Long id){
        return applicantRepository.findById(id).orElse(null);
    }
}
