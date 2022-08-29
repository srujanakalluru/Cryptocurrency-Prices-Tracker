package com.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<GlobalError> handleMessagingException(MessagingException e) {
        GlobalError error = new GlobalError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<GlobalError> handleAuthenticationFailedException(AuthenticationFailedException e) {
        GlobalError error = new GlobalError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MailAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<GlobalError> handleMailAuthenticationException(MailAuthenticationException e) {
        GlobalError error = new GlobalError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
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

    @ExceptionHandler(CryptoPricesTrackingException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GlobalError> handleCryptoPricesTrackingException(CryptoPricesTrackingException e) {
        GlobalError error = new GlobalError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GlobalError> handleUnhandledException(Exception e) {
        GlobalError error = new GlobalError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}