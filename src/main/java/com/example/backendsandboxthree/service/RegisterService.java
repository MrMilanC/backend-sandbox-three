package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.dto.NewUserDTO;
import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterService {

    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerUser(NewUserDTO newUserDTO) throws UserException {

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
