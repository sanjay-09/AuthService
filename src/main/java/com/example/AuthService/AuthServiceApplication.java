package com.example.AuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
public class AuthServiceApplication {

	public static void main(String[] args) {

        SpringApplication.run(AuthServiceApplication.class, args);



	}

}
