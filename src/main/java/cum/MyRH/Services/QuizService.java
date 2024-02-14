package cum.MyRH.Services;

import cum.MyRH.Models.DTOs.TestDto;
import cum.MyRH.Models.Entities.*;
import cum.MyRH.Models.Mappers.TestMapper;
import cum.MyRH.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository  answerRepository;
    private final ApplicantRepository applicantRepository;
    private final BadgeRepository  badgeRepository;
    private final TestMapper testMapper;

    @Autowired
    public QuizService(TestRepository testRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, ApplicantRepository applicantRepository, BadgeRepository badgeRepository, TestMapper testMapper){
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.applicantRepository = applicantRepository;
        this.badgeRepository = badgeRepository;
        this.testMapper = testMapper;
    }

    public List<Test> getAllTests() {
        List<Test> tests = testRepository.getAllTest();
        for (Test test : tests){
            List<Question> questions = questionRepository.findByTestId(test.getId());
            for (Question question : questions) {
                List<Answer> answers = answerRepository.findByQuestionId(question.getId());
                question.setAnswers(answers);
            }
            test.setQuestions(questions);
        }
        return tests;
    }

    public Test getTestById(Long id) {
        Test test = testRepository.getTestById(id);
        System.out.println("hego :"+test);
        List<Question> questions = questionRepository.findByTestId(test.getId());
        for (Question question : questions) {
            List<Answer> answers = answerRepository.findByQuestionId(question.getId());
            question.setAnswers(answers);
        }
        test.setQuestions(questions);
        return test;
    }

    @Transactional
    public Test createTest(TestDto testDto) {
        Test test = testMapper.toEntity(testDto);
        test = testRepository.save(test);

        for (Question question : testDto.getQuestions()) {
            question.setTest(test);

            question = questionRepository.save(question);

            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
                answer.setId(null);
                answerRepository.save(answer);
            }
        }

        return test;
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
