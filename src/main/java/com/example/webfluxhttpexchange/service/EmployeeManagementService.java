package com.example.webfluxhttpexchange.service;

import com.example.webfluxhttpexchange.models.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeManagementService {
    Mono<Employee> getEmployeeById(long id);
    Flux<Employee> fetchAll();
    Mono<Employee> save(Employee employee);
    Mono<Employee> update(long id, Employee employee);
    Mono<Void> delete(long id);
}
