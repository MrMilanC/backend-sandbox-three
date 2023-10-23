package com.example.backendsandboxthree.dto;

import lombok.Data;

@Data
public class NewUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String role = "ROLE_USER";

    public NewUserDTO(){}
}
