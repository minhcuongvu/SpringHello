package com.example.SpringHello.services;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.SpringHello.dto.LoginDto;
import com.example.SpringHello.utils.JwtUtils;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtils jwtUtils;

  @Override
  public String login(LoginDto loginDto) {
    try {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          loginDto.getUsernameOrEmail(),
          loginDto.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      String token = jwtUtils.generateJwtToken(authentication);
      return token;
    } catch (Exception e) {
      throw e;
    }
  }
}
