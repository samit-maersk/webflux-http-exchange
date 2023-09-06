package com.example.webfluxhttpexchange;

import com.example.webfluxhttpexchange.httpclient.EmployeeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Slf4j
public class WebfluxHttpExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxHttpExchangeApplication.class, args);
	}

	@Value("${spring.application.employeeService.url}")
	private String apiBaseUri;
	@Bean
	WebClient employeeWebClient() {
		return WebClient
				.builder()
				.baseUrl(apiBaseUri)
				//Request Filter
				//.filter(this::filter)
				.defaultStatusHandler(HttpStatusCode::isError, response -> Mono.error(new EmployeeCustomException(response.statusCode().toString())))
				.build();
	}

	@Bean
	EmployeeClient employeeClient() {
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builder(WebClientAdapter.forClient(employeeWebClient()))
				.build();
		return factory.createClient(EmployeeClient.class);
	}


	private Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
		return next
				.exchange(request)
				.flatMap(response -> {
					var httpStatus = response.statusCode();
					if (httpStatus.is2xxSuccessful()) {
						log.info("2XX RESPONSE");
						return Mono.fromCallable(() -> response);
					}
					if (httpStatus.isError()) {
						log.error("ERROR RESPONSE");
						return response
								.bodyToMono(String.class)
								.flatMap(e -> Mono.error(new EmployeeCustomException(e)));
					}
					return Mono.just(response);
				});
	}

}
