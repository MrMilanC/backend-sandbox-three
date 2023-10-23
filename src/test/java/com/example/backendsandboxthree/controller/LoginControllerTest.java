package com.example.backendsandboxthree.controller;
import com.example.backendsandboxthree.dto.LoginRequest;
import com.example.backendsandboxthree.dto.LoginResponse;
import com.example.backendsandboxthree.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        // Mock LoginRequest
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");

        // Mock the expected token response
        String expectedToken = "mocked-token";

        when(loginService.authenticateUser(any(LoginRequest.class)))
                .thenReturn(expectedToken);

        ResponseEntity<LoginResponse> response = loginController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedToken, Objects.requireNonNull(response.getBody()).getAccessToken());
        assertTrue(response.getBody().isOk());
    }
}
