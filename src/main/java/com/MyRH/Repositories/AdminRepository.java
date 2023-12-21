package com.MyRH.Repositories;

import com.MyRH.Models.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}