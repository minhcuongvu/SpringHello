package com.example.SpringHello.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringHello.jpa.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}