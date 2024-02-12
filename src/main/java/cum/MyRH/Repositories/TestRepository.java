package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("SELECT New Test(t.id , t.name) FROM Test t WHERE t.id = :id")
    Test getTestById(Long id);

    @Query("SELECT New Test(t.id , t.name) FROM Test t")
    List<Test> getAllTest();
}