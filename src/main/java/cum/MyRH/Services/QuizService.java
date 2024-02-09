package cum.MyRH.Services;

import cum.MyRH.Models.DTOs.TestDto;
import cum.MyRH.Models.Entities.Applicant;
import cum.MyRH.Models.Entities.Badge;
import cum.MyRH.Models.Entities.Test;
import cum.MyRH.Models.Mappers.TestMapper;
import cum.MyRH.Repositories.ApplicantRepository;
import cum.MyRH.Repositories.BadgeRepository;
import cum.MyRH.Repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final TestRepository testRepository;
    private final ApplicantRepository applicantRepository;
    private final BadgeRepository  badgeRepository;
    private final TestMapper testMapper;

    @Autowired
    public QuizService(TestRepository testRepository, ApplicantRepository applicantRepository, BadgeRepository badgeRepository, TestMapper testMapper){
        this.testRepository = testRepository;
        this.applicantRepository = applicantRepository;
        this.badgeRepository = badgeRepository;
        this.testMapper = testMapper;
    }

    public Test getTestById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    public Test createTest(TestDto testDto) {
        Test test = testMapper.toEntity(testDto);
        test.setQuestions(testDto.getQuestions());
        return testRepository.save(test);
    }

    public Badge createBadge(Badge badge) {
        return badgeRepository.save(badge);
    }

    public Applicant assignBadgeToApplicant(Long applicantId, Long badgeId) {
        Applicant applicant = applicantRepository.findById(applicantId).orElse(null);
        Badge badge = badgeRepository.findById(badgeId).orElse(null);
        assert applicant != null;
        applicant.getBadges().add(badge);
        return applicantRepository.save(applicant);
    }
}
