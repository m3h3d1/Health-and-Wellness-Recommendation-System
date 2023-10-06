package com.healthapp.nutritionservice.security;

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

                            // Food Controller
                            .requestMatchers(HttpMethod.POST, "/food/**").hasAnyRole("ADMIN", "NUTRITIONIST")
                            .requestMatchers(HttpMethod.GET, "/food/**").hasAnyRole("ADMIN", "NUTRITIONIST")
                            .requestMatchers(HttpMethod.PUT, "/food/**").hasAnyRole("ADMIN", "NUTRITIONIST")
                            .requestMatchers(HttpMethod.DELETE, "/food/**").hasAnyRole("ADMIN", "NUTRITIONIST")

                            // Recipe Controller
                            .requestMatchers(HttpMethod.POST, "/recipe/**").authenticated()
                            .requestMatchers(HttpMethod.GET, "/recipe/**").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/recipe/**").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/recipe/**").authenticated()

                            // Nutrition Controller
                            .requestMatchers(HttpMethod.POST, "/nutrition/add").hasAnyRole("ADMIN", "NUTRITIONIST")
                            .requestMatchers(HttpMethod.GET, "/nutrition/get-all").hasAnyRole("ADMIN", "NUTRITIONIST")
                            .requestMatchers(HttpMethod.GET, "/nutrition/get/{nutritionId}").hasAnyRole("ADMIN", "NUTRITIONIST")
                            .requestMatchers(HttpMethod.PUT, "/nutrition/update/{nutritionId}").hasAnyRole("ADMIN", "NUTRITIONIST")
                            .requestMatchers(HttpMethod.DELETE, "/nutrition/delete/{nutritionId}").hasAnyRole("ADMIN", "NUTRITIONIST")

                            // Details Controller
                            .requestMatchers(HttpMethod.POST, "/nutrition/details/**").permitAll()

                            // Recommendation Controller
                            .requestMatchers(HttpMethod.POST, "/nutrition/recommendation/**").permitAll()

                            // Allowed any other requests without authentication.
                            .anyRequest().permitAll();
                })
                // Added the custom authorization filter before the UsernamePasswordAuthenticationFilter.
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}