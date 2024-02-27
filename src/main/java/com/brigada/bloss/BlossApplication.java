package com.brigada.bloss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.brigada.listening")
@EntityScan("com.brigada.entity")
@EnableJpaRepositories("com.brigada.dao")
public class BlossApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlossApplication.class, args);
	}

}
