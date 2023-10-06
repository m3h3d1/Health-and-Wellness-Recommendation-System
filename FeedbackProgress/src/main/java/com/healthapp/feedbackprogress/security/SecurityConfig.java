package com.healthapp.feedbackprogress.security;

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
                // Disabled CSRF protection.
                .csrf(AbstractHttpConfigurer::disable)
                // Set session creation policy to STATELESS, as we are using JWT tokens.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            // Required authentication for these endpoints with specific HTTP methods.

                            // Feedback Controller
                            .requestMatchers(HttpMethod.POST, "/feedback/**").authenticated()
                            .requestMatchers(HttpMethod.GET, "/feedback/**").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/feedback/**").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/feedback/**").authenticated()

                            // Progress Controller
                            .requestMatchers(HttpMethod.GET, "/progress/**").authenticated()

                            // Allowed any other requests without authentication.
                            .anyRequest().permitAll();
                })
                // Added the custom authorization filter before the UsernamePasswordAuthenticationFilter.
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}