package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.dto.LoginRequest;
import com.example.backendsandboxthree.dto.LoginResponse;
import com.example.backendsandboxthree.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        String token = loginService.authenticateUser(loginRequest);
        return ResponseEntity.ok(LoginResponse.builder()
                .accessToken(token)
                .ok(true)
                .build());
    }

}


