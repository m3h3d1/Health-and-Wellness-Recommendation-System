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

                            // User Controller
                            .requestMatchers(HttpMethod.PUT, AppConstants.UPDATE_USER).hasRole("USER")
                            .requestMatchers(HttpMethod.POST, AppConstants.REGISTER_USER).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, AppConstants.UPDATE_USER).hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE, AppConstants.DELETE_USER).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, AppConstants.GET_USER_BY_ID).hasRole("USER")
                            .requestMatchers(HttpMethod.GET, AppConstants.GET_ALL_USERS).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, AppConstants.CHANGE_PASSWORD).hasRole("USER")
                            .requestMatchers(HttpMethod.POST, AppConstants.ASSIGN_ROLE).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, AppConstants.REMOVE_ROLE).hasRole("ADMIN")

                            //Profile Controller
                            .requestMatchers(HttpMethod.POST, AppConstants.CREATE_PROFILE).hasRole("USER")
                            .requestMatchers(HttpMethod.PUT, AppConstants.UPDATE_PROFILE).hasRole("USER")
                            .requestMatchers(HttpMethod.GET, AppConstants.GET_PROFILE_BY_ID).hasRole("USER")
                            .requestMatchers(HttpMethod.GET, AppConstants.GET_ALL_PROFILES).hasRole("ADMIN")

                            // Contact Controller
                            .requestMatchers(HttpMethod.POST, AppConstants.CREATE_CONTACT).hasRole("USER")
                            .requestMatchers(HttpMethod.PUT, AppConstants.UPDATE_CONTACT).hasRole("USER")
                            .requestMatchers(HttpMethod.GET, AppConstants.GET_CONTACT_BY_ID).hasRole("USER")
                            .requestMatchers(HttpMethod.GET, AppConstants.GET_ALL_CONTACTS).hasRole("ADMIN")


//                            .requestMatchers(HttpMethod.GET, "books/all").authenticated()
                            .anyRequest().permitAll();
                })
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}

