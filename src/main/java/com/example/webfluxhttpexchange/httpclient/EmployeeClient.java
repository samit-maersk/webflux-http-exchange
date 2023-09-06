package com.example.webfluxhttpexchange.httpclient;

import com.example.webfluxhttpexchange.models.Employee;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange(url = "/employee", contentType = MediaType.APPLICATION_JSON_VALUE)
public interface EmployeeClient {

    @GetExchange
    //@HttpExchange(url = "/employee", method = "GET")
    Flux<Employee> fetchAll();

    @GetExchange("/{id}")
    //@HttpExchange(url = "/employee/{id}", method = "GET")
    Mono<Employee> getEmployeeById(@PathVariable long id);

    @PostExchange
    //@HttpExchange(url = "/employee", method = "POST")
    Mono<Employee> save(@RequestBody Employee employee);

    @PutExchange("/{id}")
    //@HttpExchange(url = "/employee/{id}", method = "PUT")
    Mono<Employee> update(@PathVariable long id, @RequestBody Employee employee);

    @DeleteExchange("/{id}")
    //@HttpExchange(url = "/employee/{id}", method = "DELETE")
    Mono<Void> delete(@PathVariable long id);
}
