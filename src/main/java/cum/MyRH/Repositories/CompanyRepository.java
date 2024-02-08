package cum.MyRH.Repositories;

import cum.MyRH.Models.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT new Company(c.id, c.name, c.email, c.password, c.address, c.phone , c.plan, c.state) FROM Company c WHERE c.email = :email")
    Optional<Company> findCompanyWithoutImage(@Param("email") String email);

}