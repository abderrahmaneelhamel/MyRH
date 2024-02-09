package cum.MyRH.Controllers;

import cum.MyRH.Models.DTOs.TestDto;
import cum.MyRH.Models.Entities.Applicant;
import cum.MyRH.Models.Entities.Badge;
import cum.MyRH.Models.Entities.Test;
import cum.MyRH.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/test/{id}")
    public Test getTestById(@PathVariable Long id) {
        return quizService.getTestById(id);
    }

    @PostMapping("/test")
    public Test createTest(@RequestBody TestDto testDto) {
        return quizService.createTest(testDto);
    }

    @PostMapping("/badge")
    public Badge createBadge(@RequestBody Badge badge) {
        return quizService.createBadge(badge);
    }

    @PostMapping("/assignBadge/{applicantId}/{badgeId}")
    public Applicant assignBadgeToApplicant(
            @PathVariable Long applicantId,
            @PathVariable Long badgeId
    ) {
        return quizService.assignBadgeToApplicant(applicantId, badgeId);
    }
}
