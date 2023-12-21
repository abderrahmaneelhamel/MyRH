package com.MyRH.Services;

import com.MyRH.Models.DTOs.ApplicantDto;
import com.MyRH.Models.Entities.Applicant;
import com.MyRH.Models.Mappers.ApplicantMapper;
import com.MyRH.Repositories.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicantService {

    ApplicantRepository  applicantRepository;
    ApplicantMapper  applicantMapper;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository,ApplicantMapper  applicantMapper) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
    }

    public Applicant createApplicant(ApplicantDto applicantDto) {
        Applicant applicant = applicantMapper.toEntity(applicantDto);
        return applicantRepository.save(applicant);
    }

    public List<Applicant> getAllApplicants(){
        return applicantRepository.findAll();
    }

    public Applicant getApplicantById(Long id){
        return applicantRepository.findById(id).orElse(null);
    }
}
