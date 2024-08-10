package com.example.SpringHello.services;

import com.example.SpringHello.dto.LoginDto;

public interface AuthService {
  String login(LoginDto loginDto);
}