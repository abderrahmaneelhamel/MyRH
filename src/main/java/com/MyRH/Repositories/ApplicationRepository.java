package com.MyRH.Repositories;

import com.MyRH.Models.Entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a WHERE a.job.id = :jobId")
    Optional<List<Application>> findByJobId(@Param("jobId") Long jobId);
}