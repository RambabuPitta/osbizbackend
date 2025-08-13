package com.orionsolwings.osbiz.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Inject JwtAuthenticationFilter via constructor
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /*
     * ---------------- OLD CONFIG (Permit All) ----------------
     * 
     * @Bean
     * public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     *     http
     *         .csrf().disable()
     *         .authorizeHttpRequests()
     *             .anyRequest().permitAll();
     *     return http.build();
     * }
     */

    /*
     * ---------------- OLD CONFIG (HttpBasic Auth) ----------------
     * 
     * @Bean
     * public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     * 
     *     logger.info("Initializing Security Configuration...");
     * 
     *     http
     *         .csrf().disable()
     *         .authorizeHttpRequests()
     *             .requestMatchers("/api/v1/usermanagement/login").permitAll()   // Allow /login
     *             .anyRequest().authenticated()            // Secure all other endpoints
     *         .and()
     *         .httpBasic(); // Basic auth for testing
     * 
     *     return http.build();
     * }
     */

    // ---------- CURRENT WORKING JWT CONFIGURATION ----------
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Initializing Security Configuration with JWT Authentication...");

        http.csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/usermanagement/login").permitAll() // Public endpoint
                .anyRequest().authenticated() // All others need authentication
            )
            // Add JWT filter before Spring Security's UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    
}
