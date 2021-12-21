package it.unito.user_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class PiscaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiscaApplication.class, args);
	}

}
