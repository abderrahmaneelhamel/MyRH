package com.MyRH.Repositories;

import com.MyRH.Models.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByEmail(String email);
}