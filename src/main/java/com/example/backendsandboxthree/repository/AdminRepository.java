package com.example.backendsandboxthree.repository;

import com.example.backendsandboxthree.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    public Admin findByUsername(String username);
}
