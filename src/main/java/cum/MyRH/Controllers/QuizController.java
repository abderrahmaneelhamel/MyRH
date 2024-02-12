package cum.MyRH.Controllers;

import cum.MyRH.Models.DTOs.TestDto;
import cum.MyRH.Models.Entities.Applicant;
import cum.MyRH.Models.Entities.Badge;
import cum.MyRH.Models.Entities.Test;
import cum.MyRH.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/tests")
    public ResponseEntity<List<Test>> getAllTests() {
        List<Test> tests = quizService.getAllTests();
        System.out.println(tests);
        return ResponseEntity.ok(tests);
    }
    @GetMapping("/test/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable Long id) {
        Test test = quizService.getTestById(id);
        return ResponseEntity.ok(test);
    }

    @PostMapping("/test")
    public ResponseEntity<Test> createTest(@RequestBody TestDto testDto) {
        Test test = quizService.createTest(testDto);
        return ResponseEntity.ok(test);
    }

    @PostMapping("/badge")
    public ResponseEntity<Badge> createBadge(@RequestBody Badge badge) {
        Badge newBadge = quizService.createBadge(badge);
        return ResponseEntity.ok(newBadge);
    }

    @PostMapping("/assignBadge/{applicantId}/{badgeId}")
    public ResponseEntity<Applicant> assignBadgeToApplicant(
            @PathVariable Long applicantId,
            @PathVariable Long badgeId
    ) {
        Applicant applicant = quizService.assignBadgeToApplicant(applicantId, badgeId);
        return ResponseEntity.ok(applicant);
    }
}
