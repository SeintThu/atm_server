package com.java.talent.batch12.atm.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTTokenService {

    @Value("${jwt.secrettoken}")
    private String secretToken;

    @Value("${jwt.expiration}")
    private Long expirationAccessTime;


    @Value("${jwt.refreshexpiration}")
    private Long expirationRefreshTime;


    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey =
                Keys.hmacShaKeyFor(secretToken.getBytes(StandardCharsets.UTF_8));
    }

    public String getAccountIdByLoginToken(String standardToken) {
        Claims claims = extractAllClaims(standardToken);
        return claims.get("accountId", String.class);
    }

    public String getUserNameByLoginToken(String standardToken) {
        Claims claims = extractAllClaims(standardToken);
        return claims.get("username", String.class);
    }



    public String getAccountRoleFromLoginToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    /**
     * Refactored parsing engine using modern JJWT fluent builder syntax.
     * Replaces deprecated .setSigningKey() and .parseClaimsJws() helper rules.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateAccessToken( String userName,String accountId, String role ) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secretToken.getBytes(StandardCharsets.UTF_8));

        Date issueAt = new Date();
        Date expiration = new Date(System.currentTimeMillis() + expirationAccessTime);

        return Jwts.builder()
                .claim("role", role)
                .claim("username", userName)
                .claim("accountId", accountId)
                .issuedAt(issueAt).
                expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken( String userName,String accountId, String role) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secretToken.getBytes(StandardCharsets.UTF_8));

        Date issueAt = new Date();
        Date expiration = new Date(System.currentTimeMillis() + expirationRefreshTime);

        return Jwts.builder()
                .claim("role", role)
                .claim("username", userName)
                .claim("accountId", accountId)
                .issuedAt(issueAt).
                expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

}