package com.sysco.exception;

import com.sysco.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ControllerAdvice
@RestController
@Slf4j
public class RestExceptionHandler {

  @ExceptionHandler(value = SqlException.class)
  public ResponseEntity sqlException(SqlException e) {
    log.error("SqlException() : ", e);
    GenericResponse genericResponse = new GenericResponse();
    genericResponse.setErrorMessage(e.getMessage());
    genericResponse.setResult(false);
    return new ResponseEntity<Object>(genericResponse, new HttpHeaders(), e.getStatusCode());
  }


  @ExceptionHandler(value = ValidationException.class)
  public ResponseEntity validatorException(ValidationException e) {
    log.error("ValidatorException() : ", e);
    GenericResponse genericResponse = new GenericResponse();
    genericResponse.setErrorMessage(e.getMessage());
    genericResponse.setResult(false);
    return new ResponseEntity<Object>(genericResponse, new HttpHeaders(), e.getStatusCode());
  }

  @ExceptionHandler(value = AWS3Exception.class)
  public ResponseEntity aws3Exception(AWS3Exception e) {
    log.error("AWS3Exception() : ", e);
    GenericResponse genericResponse = new GenericResponse();
    genericResponse.setErrorMessage(e.getMessage());
    genericResponse.setResult(false);
    return new ResponseEntity<Object>(genericResponse, new HttpHeaders(), e.getStatusCode());
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity validException(Exception e) {
    log.error("ValidatorException() : ", e);
    MethodArgumentNotValidException c = (MethodArgumentNotValidException) e;
    List<ObjectError> errors =c.getBindingResult().getAllErrors();
    StringBuffer errorMsg = new StringBuffer();
    for (ObjectError error : errors) {
      errorMsg.append(error.getDefaultMessage()).append(";");
    }
    GenericResponse genericResponse = new GenericResponse();
    genericResponse.setErrorMessage(errorMsg.toString());
    genericResponse.setResult(false);
    return new ResponseEntity<Object>(genericResponse, new HttpHeaders(), HttpStatus.OK);
  }
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity allException(Exception e) {
    log.error("catchAll() : Internal Service Error", e);
    GenericResponse genericResponse = new GenericResponse();
    genericResponse.setErrorMessage(e.getMessage());
    genericResponse.setResult(false);
    return new ResponseEntity<Object>(genericResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
