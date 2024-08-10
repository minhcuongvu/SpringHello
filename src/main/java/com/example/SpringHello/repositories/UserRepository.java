package com.example.SpringHello.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringHello.jpa.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsernameOrEmail(String username, String email);

  Optional<User> findByUsername(String username);
}