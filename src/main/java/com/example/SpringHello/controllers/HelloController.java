package com.example.SpringHello.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/api/hello")
  public Map<String, Object> sayHello(@RequestParam(value = "q", defaultValue = "World") String name) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", "hello, 322 69420 frfr " + name + " nocap");
    String pod = getPodName();
    response.put("podName", pod);
    return response;
  }

  private String getPodName() {
    return System.getenv("HOSTNAME");
  }
}
