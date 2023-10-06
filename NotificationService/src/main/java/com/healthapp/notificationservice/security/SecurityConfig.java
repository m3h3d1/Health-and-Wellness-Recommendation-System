package com.healthapp.notificationservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // Create and return an AuthenticationManager using the provided AuthenticationConfiguration.
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                // Disable CSRF protection.
                .csrf(AbstractHttpConfigurer::disable)
                // Set session creation policy to STATELESS, as we are using JWT tokens.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            // Require authentication for these endpoints with specific HTTP methods.
                            .requestMatchers(HttpMethod.GET, "/preferences/**").authenticated()
                            .requestMatchers(HttpMethod.POST, "/preferences/**").authenticated()
                            .requestMatchers(HttpMethod.POST, "/notifications/all").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/notifications/filtered").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/notifications/set-seen/**").authenticated()
                            // Allow any other requests without authentication.
                            .anyRequest().permitAll();
                })
                // Add the custom authorization filter before the UsernamePasswordAuthenticationFilter.
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}