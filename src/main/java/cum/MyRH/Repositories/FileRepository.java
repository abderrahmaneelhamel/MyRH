package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Files, Long> {
    Files findByName(String name);
}