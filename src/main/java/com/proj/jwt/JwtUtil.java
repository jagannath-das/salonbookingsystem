package com.proj.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.proj.model.User;   // ⭐ IMPORTANT IMPORT

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    // Secret must be minimum 32 characters
    private final String SECRET =
            "mysecretkeymysecretkeymysecretkey12";

    // Convert String → Key object
    private final Key SECRET_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    /* =====================================================
       ✅ GENERATE TOKEN  (UPDATED)
    ===================================================== */
    public String generateToken(User user){

        return Jwts.builder()
                .setSubject(user.getEmailid())   // email / username
                .claim("id", user.getId())       // ⭐ ADD USER ID
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000 * 60 * 60) // 1 hour
                )
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /* =====================================================
       ✅ EXTRACT USERNAME
    ===================================================== */
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    /* =====================================================
       ✅ VALIDATE TOKEN
    ===================================================== */
    public boolean validateToken(String token,
                                 UserDetails userDetails){

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    /* =====================================================
       ✅ CHECK EXPIRY
    ===================================================== */
    private boolean isTokenExpired(String token){
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    /* =====================================================
       ✅ PARSE TOKEN
    ===================================================== */
    private Claims extractAllClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}