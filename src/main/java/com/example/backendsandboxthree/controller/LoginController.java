package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.dto.LoginRequest;
import com.example.backendsandboxthree.dto.LoginResponse;
import com.example.backendsandboxthree.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {

        String token = loginService.authenticateUser(loginRequest);

        System.out.println("Token gemacht");
        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getPassword());
        System.out.println(token);

        return LoginResponse.builder()
                .accessToken(token)
                .ok(true)
                .build();
    }
}


