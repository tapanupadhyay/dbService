package com.learning.stock.dbService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.learning.stock.repository")
@ComponentScan("com.learning.stock.resource")
@EntityScan("com.learning.stock.model")
@SpringBootApplication
public class DbServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(DbServiceApplication.class, args);
	}
}
