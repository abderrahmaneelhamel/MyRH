package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT New Answer(a.id , a.content , a.status) FROM Answer a WHERE a.question.id = :id")
    List<Answer> findByQuestionId(Long id);
}