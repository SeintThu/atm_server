package com.java.talent.batch12.atm.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${header-api-key-name}")
    private String HEADER_NAME_API_KEY;

    @Value("${jwt.secrettoken}")
    private String secret;

    @Value("${jwt.apikey}")
    private  String expectedApiKey;

    private SecretKey secretKey;

    private final JWTTokenService jwtAccountResolverService;


    private static final List<String> EXCLUDED_URIS = List.of(
            "/api/auth/login",
            "/api/auth",
            "/api/auth/signup"
    );


    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Authentication doAuthentication(HttpServletRequest request) {

        String apiKey = request.getHeader(HEADER_NAME_API_KEY);
        if (apiKey == null || !apiKey.equals(this.expectedApiKey)) {
            throw new BadCredentialsException("Invalid API Key");
        }


        String requestUri = request.getRequestURI();

        if (isExcludedURI(requestUri)) {
            return UsernamePasswordAuthenticationToken.authenticated(
                    "ANONYMOUS_GATEWAY_USER",
                    null,
                    Collections.emptyList()
            );
        }

        String authHeader = request.getHeader("Authorization");

        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null || token.trim().isEmpty()) {
            throw new BadCredentialsException("Missing Authentication Token.");
        }


        return parseAndBuildUserAuthentication(token);
    }

    private Authentication parseAndBuildUserAuthentication(String token) {
        try {
            String username = jwtAccountResolverService.getUserNameByLoginToken(token);
            String accountId =  jwtAccountResolverService.getAccountIdByLoginToken(token);
            String roleName =  jwtAccountResolverService.getAccountRoleFromLoginToken(token);


            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));

            UserPrincipal principal = new UserPrincipal(
                    accountId,
                    username,
                    roleName,
                    authorities,
                    true
            );

            return UsernamePasswordAuthenticationToken.authenticated(
                    principal,
                    null,
                    authorities
            );

        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Token has expired. Please obtain a new authentication token.", e);
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid token. Please provide a valid authentication token.", e);
        }
    }


    private boolean isExcludedURI(String uri) {
        return EXCLUDED_URIS.stream().anyMatch(uri::contains);
    }

}