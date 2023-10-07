package com.healthapp.recommendationservicemanual.security;

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
                            // Allow access to recommendations for all authenticated users
                            .requestMatchers(HttpMethod.GET, "/diet-recommendations/**", "/exercise-recommendations/**", "/mental-health-recommendations/**", "/sleep-recommendations/**")
                            .authenticated()
                            // Only users with specific roles can create/update recommendations
                            .requestMatchers(HttpMethod.POST, "/diet-recommendations/**").hasRole("DOCTOR")
                            .requestMatchers(HttpMethod.PUT, "/diet-recommendations/**").hasRole("DOCTOR")
                            .requestMatchers(HttpMethod.POST, "/exercise-recommendations/**").hasRole("TRAINER")
                            .requestMatchers(HttpMethod.PUT, "/exercise-recommendations/**").hasRole("TRAINER")
                            .requestMatchers(HttpMethod.POST, "/mental-health-recommendations/**").hasRole("PSYCHOLOGIST")
                            .requestMatchers(HttpMethod.PUT, "/mental-health-recommendations/**").hasRole("PSYCHOLOGIST")
                            // Allow DOCTOR, TRAINER, and PSYCHOLOGIST to update sleep recommendations
                            .requestMatchers(HttpMethod.PUT, "/sleep-recommendations/**").hasAnyRole("DOCTOR", "TRAINER", "PSYCHOLOGIST")
                            // Only ADMIN can delete recommendations
                            .requestMatchers(HttpMethod.DELETE, "/diet-recommendations/**", "/exercise-recommendations/**", "/mental-health-recommendations/**", "/sleep-recommendations/**")
                            .hasRole("ADMIN")
                            // Allow all other requests without authentication
                            .anyRequest().permitAll();
                })
                // Add the custom authorization filter before the UsernamePasswordAuthenticationFilter.
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}