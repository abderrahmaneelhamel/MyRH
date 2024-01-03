package com.MyRH.Repositories;

import com.MyRH.Models.Entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}