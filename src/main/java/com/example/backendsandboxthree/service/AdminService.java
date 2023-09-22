package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.exception.AdminException;
import com.example.backendsandboxthree.model.Admin;
import com.example.backendsandboxthree.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

        public String createNewAdmin() throws AdminException {
        // TODO Auto-generated method stub

        String name = "Admin";
        String email = "admin@gmail.com";
        String password = "password";

        Admin existsAdmin = adminRepository.findByUsername(email);
        if (existsAdmin != null)
            throw new AdminException("Admin already created, the credentials => Email : " + email + ", Password : " + password);

        Admin admin = new Admin();
        admin.setName(name);
        admin.setUsername(email);
        admin.setPassword(password);

        adminRepository.save(admin);

        return "Admin created => Email : " + email + ", Password : " + password;
    }

}
