package com.example.webfluxhttpexchange.service;

import com.example.webfluxhttpexchange.httpclient.EmployeeClient;
import com.example.webfluxhttpexchange.models.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeManagementHttpExchangeClient implements EmployeeManagementService {

    final EmployeeClient employeeClient;
    @Override
    public Mono<Employee> getEmployeeById(long id) {
        return employeeClient.getEmployeeById(id);
    }

    @Override
    public Flux<Employee> fetchAll() {
        return employeeClient.fetchAll();
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        return employeeClient.save(employee);
    }

    @Override
    public Mono<Employee> update(long id, Employee employee) {
        return employeeClient.update(id, employee);
    }

    @Override
    public Mono<Void> delete(long id) {
        return employeeClient.delete(id);
    }
}
