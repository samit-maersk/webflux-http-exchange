package com.example.webfluxhttpexchange;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EmployeeCustomException extends RuntimeException {
    public EmployeeCustomException(String message) {
        super(message);
    }
}
