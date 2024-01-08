package com.MyRH.Repositories;

import com.MyRH.Models.Entities.Company;
import com.MyRH.Models.Entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT count(j) FROM Job j WHERE j.company = :company")
    int findJobsByCompany(Company company);
}