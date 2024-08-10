package com.example.SpringHello.configs;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");

        // Determine status code and message based on the type of exception
        int statusCode;
        String message;

        if (authException instanceof BadCredentialsException) {
            statusCode = HttpServletResponse.SC_UNAUTHORIZED; // 401
            message = "Invalid username or password";
        } else if (authException instanceof InsufficientAuthenticationException) {
            statusCode = HttpServletResponse.SC_UNAUTHORIZED; // 401
            message = "Insufficient authentication";
        } else {
            statusCode = HttpServletResponse.SC_FORBIDDEN; // 403 - Auth successful but denied/lack permissions
            message = "Access denied";
        }

        response.setStatus(statusCode);

        String jsonResponse = "{\"error\": \""
                + (statusCode == HttpServletResponse.SC_UNAUTHORIZED ? "Unauthorized" : "Forbidden")
                + "\", \"message\": \"" + message + "\"}";

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }
}