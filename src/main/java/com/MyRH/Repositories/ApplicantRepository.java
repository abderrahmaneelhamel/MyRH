package com.MyRH.Repositories;

import com.MyRH.Models.Entities.Applicant;
import com.MyRH.Models.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    @Query("SELECT new Applicant(a.id, a.firstName,a.lastName, a.email, a.password, a.level, a.profile, a.city) FROM Applicant a WHERE a.email = :email")
    Optional<Applicant> findApplicantWithoutCv(@Param("email") String email);
}