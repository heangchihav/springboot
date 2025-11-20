package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Environment environment;

    public SecurityConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Get active profiles
        String[] activeProfiles = environment.getActiveProfiles();
        boolean isDev = activeProfiles.length == 0 ||
                java.util.Arrays.asList(activeProfiles).contains("dev") ||
                java.util.Arrays.asList(activeProfiles).contains("default");

        if (isDev) {
            // For development: allow all API endpoints without authentication
            http.authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll());
        } else {
            // For production: require authentication except for public endpoints
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/api/health",
                            "/api/public/**",
                            "/error",
                            "/favicon.ico")
                    .permitAll()
                    .anyRequest().authenticated())
                    .httpBasic(httpBasic -> {
                    });
        }

        return http.build();
    }
}
