package com.pm.authservice.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key secretKey;

    // secretKey jangan di store as text di code ya, security risk.
    public JwtUtil(@Value("${jwt.secret}") String secret) {

        // convert secret yang kita terima sebagai keyBytes
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
            // subject merupakan standard field yang mainly digunakan untuk store id yang relates dengan orang yang mencoba login
            .subject(email)

            // claim merupakan custom property yang dapat kita tambah ke jwt
            .claim("role", role)

            // Determine tokennya valid apa nggak
            .issuedAt(new Date())

            // kaduluwarsa dalam 10 jam
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))

            .signWith(secretKey)

            // compact akan menyatukan semuanya kedalam 1 single string
            .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token);
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT Signature");
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT");
        }
    }
}
