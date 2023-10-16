package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.dto.NewUserDTO;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.service.RegisterService;
import com.example.backendsandboxthree.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63343", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")
public class RegisterController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final RegisterService registrationService;

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody @Valid NewUserDTO newUserDTO) throws UserException {

        return registrationService.registerUser(newUserDTO);
    }
}



