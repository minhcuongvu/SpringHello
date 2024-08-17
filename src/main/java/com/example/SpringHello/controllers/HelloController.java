package com.example.SpringHello.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    response.put("message", "hello, 69420 " + name + " nocap frfr og");
    String pod = getPodName();
    response.put("podName", pod);
    // kafkaProducerService.sendMessage("Hello kafka");
    Optional<User> user = userRepository.findByUsername(name);
    if (user.isPresent()) {
      Set<Role> userRoles = roleRepository.getByUserId(user.get().getId());
      user.get().setRoles(userRoles);
    }
    response.put("user", user);
    return response;
  }

  private String getPodName() {
    return System.getenv("HOSTNAME");
  }
}
