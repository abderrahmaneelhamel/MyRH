package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}