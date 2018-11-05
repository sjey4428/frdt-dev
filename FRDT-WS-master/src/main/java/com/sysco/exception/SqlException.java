package com.sysco.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Created by james.zhu on 2018/8/24.
 */
@Getter
@Setter
@AllArgsConstructor
public class SqlException extends RuntimeException{
    private HttpStatus statusCode;
    private String message;
}
