package it.unito.apygatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ApygatewayserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApygatewayserviceApplication.class, args);
	}

}
