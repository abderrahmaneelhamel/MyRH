package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Company;
import cum.MyRH.Models.Entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT count(j) FROM Job j WHERE j.company = :company")
    int findJobsByCompany(Company company);
}