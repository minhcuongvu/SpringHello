package com.example.SpringHello.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    String jsonResponse = "{\"error\": \"Forbidden\", \"message\": \"You do not have permission to access this resource.\"}";

    try (PrintWriter out = response.getWriter()) {
      out.print(jsonResponse);
      out.flush();
    }
  }
}
