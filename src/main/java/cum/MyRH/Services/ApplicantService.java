package cum.MyRH.Services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cum.MyRH.Models.DTOs.ApplicantDto;
import cum.MyRH.Models.DTOs.ApplicationDto;
import cum.MyRH.Models.Entities.Applicant;
import cum.MyRH.Models.Entities.Application;
import cum.MyRH.Models.Entities.Files;
import cum.MyRH.Models.Enums.State;
import cum.MyRH.Models.Mappers.ApplicantMapper;
import cum.MyRH.Models.Mappers.ApplicationMapper;
import cum.MyRH.Repositories.ApplicantRepository;
import cum.MyRH.Repositories.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ApplicantService {

    ApplicantRepository  applicantRepository;
    ApplicationRepository applicationRepository;
    JobService jobService;
    FileService fileService;
    ApplicantMapper  applicantMapper;
    ApplicationMapper applicationMapper;


    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository,ApplicantMapper  applicantMapper,ApplicationMapper applicationMapper,ApplicationRepository applicationRepository,JobService jobService,FileService fileService) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
        this.applicationMapper = applicationMapper;
        this.applicationRepository = applicationRepository;
        this.jobService = jobService;
        this.fileService = fileService;
    }

    public Applicant createApplicant(ApplicantDto applicantDto) throws IOException {
        Applicant applicant = applicantMapper.toEntity(applicantDto);
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setPassword(applicantDto.getPassword());
        Files cv = fileService.storeDataIntoFileSystem(applicantDto.getCv());
        applicant.setCv(cv);
        String HashedPassword = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, applicant.getPassword().toCharArray());
        applicant.setPassword(HashedPassword);
        return applicantRepository.save(applicant);
    }

    public Applicant AuthenticateApplicant(String email, String password){
        Applicant applicant = applicantRepository.findApplicantWithoutCv(email).orElse(null);
        if (applicant != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), applicant.getPassword());
            if (result.verified) {
                applicant.setState(State.ONLINE);
                return applicantRepository.save(applicant);
            }
        }
        return null;
    }
    public void logout(Long id) {
        Applicant applicant = applicantRepository.findById(id).orElse(null);
        assert applicant != null;
        applicant.setState(State.OFFLINE);
        applicantRepository.save(applicant);
    }

    public List<Applicant> getAllApplicants(){
        return applicantRepository.findAll();
    }

    public Application applyToJob(ApplicationDto applicationDto){
        Application application = applicationMapper.toEntity(applicationDto);
        application.setApplicant(this.getApplicantById(applicationDto.getApplicant_id()));
        application.setJob(jobService.getJobById(applicationDto.getJob_id()));
        application.setStatus(applicationDto.getStatus());
        return applicationRepository.save(application);
    }

    @Transactional
    public List<Application> getApplicationsById(Long id){
        return applicationRepository.findByApplicantId(id).orElse(null);
    }

    @Transactional
    public Applicant getApplicantById(Long id){
        return applicantRepository.findById(id).orElse(null);
    }
}
