package com.duyan.auracle.auracle_backend.config;

import com.duyan.auracle.auracle_backend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // disable CSRF
                .csrf(csrf -> csrf.disable())

                // configure CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // configure authorization rules
                .authorizeHttpRequests(auth -> auth
                        // public endpoints (no auth required)
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("api/health").permitAll()

                        // all other endpoints require auth
                        .anyRequest().authenticated()
                )

                // stateless session (no server-side sessions)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // add JWT filter before Spring Secuirty's authentication filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // allow requests from frontend
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

        // allow HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // allow headers
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // allow credentials
        configuration.setAllowCredentials(true);

        // how long browsers can cache CORS preflight response (1 hr)
        configuration.setMaxAge(3600L);

        // apply CORS configuration to all pahts
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
