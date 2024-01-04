package com.MyRH.Repositories;

import com.MyRH.Models.Entities.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Applicant findByEmail(String email);
}