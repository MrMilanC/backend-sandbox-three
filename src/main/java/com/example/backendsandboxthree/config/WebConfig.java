package com.example.backendsandboxthree.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //.allowedOrigins("*")
                .allowedOrigins("http://localhost:63343")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
                .allowedHeaders("*");
                //.allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/frontend-webshop-main/img/user-files/**")
                .addResourceLocations("file:C:/Users/milan/IdeaProjects/backend-sandbox-three/src/main/resources/static/img/");
    }
}