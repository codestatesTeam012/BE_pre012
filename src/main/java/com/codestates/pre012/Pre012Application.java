package com.codestates.pre012;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Pre012Application {

	public static void main(String[] args) {
		SpringApplication.run(Pre012Application.class, args);
	}

}
