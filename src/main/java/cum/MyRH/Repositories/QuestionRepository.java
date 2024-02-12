package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT New Question(q.id , q.question) FROM Question q WHERE q.test.id = :id")
    List<Question> findByTestId(Long id);
}