package com.example.SpringHello.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  public Role() {
  }

  public Role(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
