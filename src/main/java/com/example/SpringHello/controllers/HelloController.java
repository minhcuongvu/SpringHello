package com.example.SpringHello.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringHello.services.KafkaProducerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class HelloController {

  @Autowired
  KafkaProducerService kafkaProducerService;

  @GetMapping("/api/hello")
  public Map<String, Object> sayHello(@RequestParam(value = "q", defaultValue = "World") String name) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", "hello, 322 " + name + " nocap");
    String pod = getPodName();
    response.put("podName", pod);
    kafkaProducerService.sendMessage("Hello kafka");
    return response;
  }

  private String getPodName() {
    return System.getenv("HOSTNAME");
  }
}
