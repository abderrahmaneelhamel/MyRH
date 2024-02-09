package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}