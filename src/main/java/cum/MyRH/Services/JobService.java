package cum.MyRH.Services;

import cum.MyRH.Exceptions.UpgradeplanException;
import cum.MyRH.Models.DTOs.JobDto;
import cum.MyRH.Models.Entities.Application;
import cum.MyRH.Models.Entities.Company;
import cum.MyRH.Models.Entities.Job;
import cum.MyRH.Models.Enums.Status;
import cum.MyRH.Models.Mappers.JobMapper;
import cum.MyRH.Repositories.ApplicationRepository;
import cum.MyRH.Repositories.CompanyRepository;
import cum.MyRH.Repositories.JobRepository;
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

    @Transactional
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
    public List<Job> updateJob(Long id, Status status){
        Job job =  jobRepository.findById(id).orElse(null);
        if(job != null){
            job.setStatus(status);
            jobRepository.save(job);
            return jobRepository.findAll();
        }else {
            return null;
        }
    }
}
