package com.example.SpringHello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// @SpringBootApplication(exclude = {
// 		org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
// 		org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
// })
@SpringBootApplication(scanBasePackages = "com.example")
@ComponentScan(basePackages = {
		"com.example.SpringHello.controllers",
		// "com.example.SpringHello.repositories",
		"com.example.SpringHello.services",
		"com.example.SpringHello.configs",
		"com.example.SpringHello.utils",
		"com.example.SpringHello.handlers"
})
public class SpringHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringHelloApplication.class, args);
	}
}
