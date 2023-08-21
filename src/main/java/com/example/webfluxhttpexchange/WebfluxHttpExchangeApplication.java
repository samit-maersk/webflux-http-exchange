package com.example.webfluxhttpexchange;

import com.example.webfluxhttpexchange.utility.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
				//Request Filter
				.filter((request, next) -> next
						.exchange(request)
						.flatMap(response -> {
							var httpStatus = response.statusCode();
							if(httpStatus.is4xxClientError()) {
								return Mono.error(new EmployeeNotFoundException(""));
							}
							return Mono.just(response);
						}))
				.build();
	}

}
