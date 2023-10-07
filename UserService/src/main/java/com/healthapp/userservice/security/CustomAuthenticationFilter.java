package com.healthapp.userservice.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthapp.userservice.SpringApplicationContext;
import com.healthapp.userservice.model.Requestdto.UserLoginRequestDto;
import com.healthapp.userservice.model.Responsedto.UserResponseDto;
import com.healthapp.userservice.service.interfaces.UserService;
import com.healthapp.userservice.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequestDto creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestDto.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword())
            );
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().write("Authentication failed: Please provide proper input data!");
                response.getWriter().flush();
            } catch (IOException ex) {
                return null;
            }
            return null;
        } catch (InternalAuthenticationServiceException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().write("Authentication failed: Email or password is incorrect!");
                response.getWriter().flush();
            } catch (IOException ex) {
                return null;
            }
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userId = ((User) authResult.getPrincipal()).getUsername();
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserResponseDto userResponseDto = userService.getUserById(UUID.fromString(userId));
        String accessToken = JWTUtils.generateToken(userId, userResponseDto.getRoles()
                .stream().map(role -> "ROLE_"+role.getRoleName()).collect(Collectors.toList()));

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("userName", userResponseDto.getUserName());
        responseBody.put("accessToken", accessToken);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseBody);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try {
            throw failed;
        } catch (BadCredentialsException e) {
            response.getWriter().write("Authentication failed: Email or password is incorrect!");
        }

        response.getWriter().flush();
    }
}

