package com.duyan.auracle.auracle_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
public class JwtService {

    @Value("${clerk.jwks.url}")
    private String jwksUrl;

    public Claims validateToken(String token) {
        try {
            // parse and verify the JWT token
            return Jwts.parserBuilder()
                    .setSigningKeyResolver(new ClerkJwksKeyResolver(jwksUrl))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String getUserIdFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }

    public String getEmailFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("email", String.class);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("username", String.class);
    }
}
