package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}