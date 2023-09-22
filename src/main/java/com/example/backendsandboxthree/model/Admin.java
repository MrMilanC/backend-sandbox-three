package com.example.backendsandboxthree.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Name is required")
    private String username;
    @NotNull(message = "Email is required")
    @Email(message = "Email format required")
    private String email;
    @Size(min = 6, max = 16, message = "Password should between 6 to 16 character")
    private String password;
}
