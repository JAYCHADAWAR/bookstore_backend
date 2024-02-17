package com.bookstore.website.util;
import java.util.*;

import com.bookstore.website.controller.UserController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key";
    private static final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    public static String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        log.info("in generate token with id"+userId);
        Map<String, String> claims = new HashMap<>();

        claims.put("id", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public UUID getUserIdFromToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        byte[] decodedBytes = Base64.getUrlDecoder().decode(claims.getSubject());
        String decodedUUID = new String(decodedBytes);
        return UUID.fromString(claims.getSubject());
    }
}
