package com.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CryptoPricesTrackingException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GlobalError> handleCryptoPricesTrackingException(CryptoPricesTrackingException e) {
        GlobalError error = new GlobalError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalError> handleConstraintViolationException(ConstraintViolationException e) {
        GlobalError error = new GlobalError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        GlobalError error = new GlobalError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}