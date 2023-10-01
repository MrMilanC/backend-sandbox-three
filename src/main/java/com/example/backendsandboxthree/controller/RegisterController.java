package com.example.backendsandboxthree.controller;

import com.example.backendsandboxthree.dto.NewUserDTO;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.service.UserService;
import com.example.backendsandboxthree.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:63343")
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:63343", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody @Valid NewUserDTO newUserDTO) throws UserException {

        // checking for username exists in a database
        if(userService.doesUsernameExist(newUserDTO.getUsername())){
            return new ResponseEntity<>("Username is already exist!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFirstName(newUserDTO.getFirstName());
        user.setLastName(newUserDTO.getLastName());
        user.setUsername(newUserDTO.getUsername());
        user.setEmail(newUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        user.setRole("ROLE_USER");

        userService.addUser(user);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }
}



