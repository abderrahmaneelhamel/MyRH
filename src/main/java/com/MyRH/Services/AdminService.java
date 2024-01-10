package com.MyRH.Services;

import com.MyRH.Models.DTOs.AdminDto;
import com.MyRH.Models.Entities.Admin;
import com.MyRH.Models.Enums.State;
import com.MyRH.Models.Mappers.AdminMapper;
import com.MyRH.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;


import java.util.List;

@Service
public class AdminService {

    AdminRepository  adminRepository;
    AdminMapper  adminMapper;

    @Autowired
    public AdminService(AdminRepository adminRepository,AdminMapper  adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    public Admin createAdmin(AdminDto adminDto) {
        Admin admin = adminMapper.toEntity(adminDto);
        String HashedPassword = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, admin.getPassword().toCharArray());
        admin.setPassword(HashedPassword);
        return adminRepository.save(admin);
    }

    public Admin AuthenticateAdmin(AdminDto adminDto) {
        Admin admin = adminRepository.findByEmail(adminDto.getEmail());
        if (admin != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(adminDto.getPassword().toCharArray(), admin.getPassword());
            if (result.verified) {
                admin.setState(State.ONLINE);
                return adminRepository.save(admin);
            }
        }
        return null;
    }
    public void logout(Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        assert admin != null;
        admin.setState(State.OFFLINE);
        adminRepository.save(admin);
    }
    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id){
        return adminRepository.findById(id).orElse(null);
    }
}
