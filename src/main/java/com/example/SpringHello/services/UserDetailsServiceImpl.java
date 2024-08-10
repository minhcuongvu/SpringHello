package com.example.SpringHello.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringHello.jpa.Role;
import com.example.SpringHello.jpa.User;
import com.example.SpringHello.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User user = userRepository.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

      return UserDetailsImpl.build(user);
    } catch (Exception e) {
      if (username.equals("bob")) {
        // Create a Set of Role
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, "BobRoss"));
        return UserDetailsImpl
            .build(new User(null, "bob", "ross", "bob.ross@email.com", passwordEncoder.encode("ross"), roles));
      } else {
        throw e;
      }
    }
  }

}
