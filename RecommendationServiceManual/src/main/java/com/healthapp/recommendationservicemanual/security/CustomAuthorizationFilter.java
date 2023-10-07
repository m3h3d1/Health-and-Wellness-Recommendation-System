package com.healthapp.recommendationservicemanual.security;

import com.healthapp.recommendationservicemanual.utilities.constants.TokenConstants;
import com.healthapp.recommendationservicemanual.utilities.token.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomAuthorizationFilter is a servlet filter responsible for handling JWT-based authentication.
 */
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    /**
     * Filters incoming HTTP requests, checks for JWT token in the request header, and sets the authentication context if a valid token is found.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain to continue processing the request.
     * @throws ServletException If an error occurs during the filter execution.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(TokenConstants.HEADER_STRING);
        if (header == null || !header.startsWith(TokenConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
        } else {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(header);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Parses the JWT token from the request header and creates an authentication token with user details and roles.
     *
     * @param header The JWT token from the request header.
     * @return A UsernamePasswordAuthenticationToken representing the user's authentication.
     */
    private UsernamePasswordAuthenticationToken getAuthenticationToken(String header) {
        if (header != null) {
            String token = header.replace(TokenConstants.TOKEN_PREFIX, "");
            String user = JWTUtils.hasTokenExpired(token) ? null : JWTUtils.extractUser(token);

            if (user != null) {
                List<String> userRoles = JWTUtils.extractUserRoles(token);
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (String role : userRoles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        }
        return null;
    }
}
