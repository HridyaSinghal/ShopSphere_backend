package com.example.SummerProject.Util;

import com.example.SummerProject.Entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class UserJwtUtil {

    @Autowired
    private SecretKey jwtSecretKey;

    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .claim("userName", user.getName())
                .claim("email", user.getEmailId())
                .claim("userRole", user.getRoles())
                .claim("address", user.getAddress())
                .setSubject(user.getEmailId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        if (token == null || token.trim().isEmpty() || token.chars().filter(ch -> ch == '.').count() != 2) {
            throw new IllegalArgumentException("Invalid JWT: must contain exactly 2 period characters.");
        }
        return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token).getBody();
    }

    public String extractEmail(String token) {
        if (token == null || token.trim().isEmpty() || token.chars().filter(ch -> ch == '.').count() != 2) {
            throw new IllegalArgumentException("Invalid JWT: must contain exactly 2 period characters.");
        }
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractEmail(token).equals(userDetails.getUsername());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }



}
