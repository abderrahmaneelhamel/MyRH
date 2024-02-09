package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}