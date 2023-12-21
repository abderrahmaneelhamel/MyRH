package com.MyRH.Controllers;

import com.MyRH.Models.DTOs.AdminDto;
import com.MyRH.Models.Entities.Admin;
import com.MyRH.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    AdminService  adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmin(){
        List<Admin> Admins = adminService.getAllAdmins();
        return ResponseEntity.ok(Admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Long id) {
         Admin admin = adminService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @PostMapping
    public ResponseEntity<Admin> addAdmin(@RequestBody AdminDto admin) {
        Admin newAdmin = adminService.createAdmin(admin);
        return ResponseEntity.ok(newAdmin);
    }

    @PostMapping("/authinticate")
    public ResponseEntity authinticateAdmin(@RequestBody AdminDto admin) {
        Admin authinticatedAdmin = adminService.AuthenticateAdmin(admin);
        return ResponseEntity.ok(Objects.requireNonNullElse(authinticatedAdmin, "Please enter correct email and password"));
    }
}
