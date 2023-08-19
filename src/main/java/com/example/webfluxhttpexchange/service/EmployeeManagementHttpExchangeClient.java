package com.example.webfluxhttpexchange.service;

import com.example.webfluxhttpexchange.model.Employee;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeManagementHttpExchangeClient implements EmployeeManagementService {
    @Override
    public Mono<Employee> getEmployeeById(long id) {
        return null;
    }

    @Override
    public Flux<Employee> fetchAll() {
        return null;
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        return null;
    }

    @Override
    public Mono<Employee> update(long id, Employee employee) {
        return null;
    }

    @Override
    public Mono<Void> delete(long id) {
        return null;
    }
}
