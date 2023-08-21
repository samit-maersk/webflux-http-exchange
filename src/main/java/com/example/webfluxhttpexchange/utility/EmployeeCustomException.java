package com.example.webfluxhttpexchange.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_EXTENDED)
public class EmployeeCustomException extends RuntimeException {
    public EmployeeCustomException(String message) {
        super(message);
    }
}
