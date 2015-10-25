package com.ab.irh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.ab.irh.repository")
public class IrhApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrhApplication.class, args);
	}
}
