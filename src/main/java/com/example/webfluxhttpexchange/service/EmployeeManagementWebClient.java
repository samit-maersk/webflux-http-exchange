package com.example.webfluxhttpexchange.service;

import com.example.webfluxhttpexchange.model.Employee;
import com.example.webfluxhttpexchange.utility.EmployeeCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeManagementWebClient implements EmployeeManagementService {

    final WebClient employeeWebClient;

    @Override
    public Mono<Employee> getEmployeeById(long id) {
        return employeeWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/employee/{id}").build(id))
                .retrieve()
                .bodyToMono(Employee.class)
                .onErrorContinue((e,e1) -> new EmployeeCustomException("not found"));
    }

    @Override
    public Flux<Employee> fetchAll() {
        return employeeWebClient
                .get()
                .uri("/employee")
                .retrieve()
                .bodyToFlux(Employee.class);
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        return employeeWebClient
                .post()
                .uri("/employee")
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(Employee.class);
    }

    @Override
    public Mono<Employee> update(long id, Employee employee) {
        return employeeWebClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/employee/{id}")
                        .build(id))
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(Employee.class);
    }

    @Override
    public Mono<Void> delete(long id) {
        return employeeWebClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/employee/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(Void.class);
    }
}
