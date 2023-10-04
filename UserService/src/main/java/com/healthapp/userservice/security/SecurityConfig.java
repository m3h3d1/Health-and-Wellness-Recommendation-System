package com.healthapp.userservice.security;

import com.healthapp.userservice.constants.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {
        http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.POST, AppConstants.SIGN_IN, AppConstants.SIGN_UP).permitAll()

//                            .requestMatchers(HttpMethod.GET, "/users/{userId}").hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.GET, "/users/{userId}/books").authenticated()
//                            .requestMatchers(HttpMethod.GET, "/users/{userId}/borrowed-books").authenticated()

//                            .requestMatchers(HttpMethod.POST, "/books/create").hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.PUT, "/books/update").hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.DELETE, "/books/delete").hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.GET, "books/all").authenticated()
                            .anyRequest().permitAll();
                })
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}

