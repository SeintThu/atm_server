package com.java.talent.batch12.atm.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.talent.batch12.atm.response.CommonResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        String[] segments = requestUri.split("/");
        String apiId = segments[segments.length - 1];

        if (requestUri.startsWith("/swagger-ui") ||
                requestUri.startsWith("/v3/api-docs") ||
                requestUri.equals("/swagger-ui.html")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = authenticationService.doAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try (PrintWriter writer = response.getWriter()) {
                writer.print(convertObjectToJson(CommonResponse.of(
                        401,
                        requestUri,
                        apiId,
                        "overview",
                        e.getMessage(),
                        java.time.LocalDateTime.now())));
                writer.flush();
            }
            return;
        }

        chain.doFilter(request, response);
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }
}