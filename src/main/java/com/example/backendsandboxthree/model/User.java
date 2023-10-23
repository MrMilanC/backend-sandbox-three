package com.example.backendsandboxthree.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    //private boolean isActive;
    private String role;
    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;
}

