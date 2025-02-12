package com.example.SpringHello.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringHello.models.Role;
import com.example.SpringHello.models.User;
import com.example.SpringHello.repositories.RoleRepository;
import com.example.SpringHello.repositories.UserRepository;
import com.example.SpringHello.services.KafkaProducerService;
import lombok.AllArgsConstructor;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@AllArgsConstructor
public class HelloController {
  @Autowired
  KafkaProducerService kafkaProducerService;
  @Autowired
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;

  @GetMapping("/api/hello")
  public Map<String, Object> sayHello(@RequestParam(value = "q", defaultValue = "World") String name) {
    Map<String, Object> response = new HashMap<>();
    List<String> messages = new ArrayList<>();
    // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    // messages.add(encoder.encode("password123"));

    // Add greeting message
    messages.add("hello, 69420 " + name + " nocap frfr og");

    // Get pod name
    String pod = getPodName();
    response.put("podName", pod);

    // Send Kafka message
    try {
      kafkaProducerService.sendMessage("Hello kafka");
      messages.add("Kafka message sent successfully");
    } catch (Exception e) {
      messages.add("Error sending Kafka message: " + e.getMessage());
    }

    // Get user and roles
    try {
      Optional<User> user = userRepository.findByUsername(name);
      if (user.isPresent()) {
        try {
          Set<Role> userRoles = roleRepository.getByUserId(user.get().getId());
          user.get().setRoles(userRoles);
          messages.add("User roles retrieved successfully");
        } catch (Exception e) {
          messages.add("Error retrieving user roles: " + e.getMessage());
        }
      } else {
        messages.add("User not found: " + name);
      }
      response.put("user", user);
    } catch (Exception e) {
      messages.add("Error retrieving user: " + e.getMessage());
    }

    response.put("messages", messages);
    return response;
  }

  private String getPodName() {
    return System.getenv("HOSTNAME");
  }
}
