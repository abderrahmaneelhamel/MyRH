package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}