package com.example.webfluxhttpexchange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebfluxHttpExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxHttpExchangeApplication.class, args);
	}

	@Bean
	WebClient employeeWebClient(@Value("${spring.application.employeeService.url}")String uri) {
		return WebClient
				.builder()
				.baseUrl(uri)
				.build();
	}

}
