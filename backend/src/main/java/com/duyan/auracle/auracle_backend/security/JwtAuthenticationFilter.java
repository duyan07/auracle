package com.duyan.auracle.auracle_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // extract JWT from Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                // remove "Bearer" prefix to get the actual token
                String token = authHeader.substring(7);

                // validate token and extract user info
                String userId = jwtService.getUserIdFromToken(token);

                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // create authentication object
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // set authentication in Spring Security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // token is invalid - continue without authentiction
                logger.error("JWT validation failed: " + e.getMessage());
            }
        }

        // continue filter chain
        filterChain.doFilter(request, response);
    }
}
