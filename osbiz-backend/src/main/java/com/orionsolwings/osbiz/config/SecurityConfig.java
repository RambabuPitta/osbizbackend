package com.orionsolwings.osbiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for APIs (not recommended for browser forms)
            .authorizeHttpRequests()
                .anyRequest().permitAll(); // Allow all endpoints without authentication

        return http.build();
    }
}
