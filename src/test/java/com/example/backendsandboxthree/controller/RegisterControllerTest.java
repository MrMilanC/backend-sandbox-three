package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.dto.NewUserDTO;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.service.RegisterService;
import com.example.backendsandboxthree.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterControllerTest {

    @InjectMocks
    private RegisterController registerController;

    @Mock
    private UserService userService;

    @Mock
    private RegisterService registrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() throws UserException {
        // Create a sample NewUserDTO
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername("testuser");
        newUserDTO.setPassword("password");

        // Mock the behavior of the registration service using doReturn
        ResponseEntity<String> mockResponse = ResponseEntity.status(HttpStatus.OK).body("User registered successfully");
        doReturn(mockResponse).when(registrationService).registerUser(any(NewUserDTO.class));

        // Call the controller method
        ResponseEntity<?> response = registerController.registerUser(newUserDTO);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }
}
