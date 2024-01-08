package com.MyRH.Repositories;

import com.MyRH.Models.Entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}