package com.sysco.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private HttpStatus statusCode;
    private String message;
}
