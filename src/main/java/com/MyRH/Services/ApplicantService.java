package com.MyRH.Services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.MyRH.Models.DTOs.ApplicantDto;
import com.MyRH.Models.DTOs.ApplicationDto;
import com.MyRH.Models.Entities.Applicant;
import com.MyRH.Models.Entities.Application;
import com.MyRH.Models.Entities.Company;
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
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setPassword(applicantDto.getPassword());
        String HashedPassword = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, applicant.getPassword().toCharArray());
        applicant.setPassword(HashedPassword);
        return applicantRepository.save(applicant);
    }

    public Applicant AuthenticateApplicant(String email, String password){
        Applicant applicant = applicantRepository.findByEmail(email);
        if (applicant != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), applicant.getPassword());
            if (result.verified) {
                return applicant;
            }
        }
        return null;
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
