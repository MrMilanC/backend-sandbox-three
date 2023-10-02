package com.example.backendsandboxthree.security;

import com.example.backendsandboxthree.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(AbstractHttpConfigurer::disable)
            //.securityMatcher("/admin/**")
            .authorizeHttpRequests((registry) -> registry
                     .requestMatchers("/").permitAll()
                     .requestMatchers("/login").permitAll()
                     .requestMatchers("/register/create").permitAll()
                     //.requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                     .requestMatchers("/products/**").permitAll()
                     .requestMatchers("/admin/**").permitAll()
                     //.requestMatchers("/admin/**").hasRole("ADMIN") //correct
                     //.requestMatchers("/products/**").hasAuthority("ROLE_USER")
                     //.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") //correct
                     //.requestMatchers("/products/**").hasAnyRole("ADMIN", "USER")
                     //.requestMatchers("/products/**").hasRole("USER")
                     .anyRequest().authenticated()
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
        http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
