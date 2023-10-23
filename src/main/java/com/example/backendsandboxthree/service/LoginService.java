package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.dto.LoginRequest;
import com.example.backendsandboxthree.security.jwt.JwtIssuer;
import com.example.backendsandboxthree.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public String authenticateUser(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        List<String> roles = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return jwtIssuer.issue(
                principal.getUserId(),
                principal.getUsername(),
                roles);
    }
}
