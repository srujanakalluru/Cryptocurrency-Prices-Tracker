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
    public ResponseEntity<GlobalError> handleMessagingException(MessagingException messagingException) {
        GlobalError error = new GlobalError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, messagingException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<GlobalError> handleAuthenticationFailedException(AuthenticationFailedException authenticationFailedException) {
        GlobalError error = new GlobalError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, authenticationFailedException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MailAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<GlobalError> handleMailAuthenticationException(MailAuthenticationException mailAuthenticationException) {
        GlobalError error = new GlobalError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, mailAuthenticationException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalError> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        GlobalError error = new GlobalError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, constraintViolationException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        GlobalError error = new GlobalError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, methodArgumentTypeMismatchException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CryptoPricesTrackingException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GlobalError> handleCryptoPricesTrackingException(CryptoPricesTrackingException cryptoPricesTrackingException) {
        GlobalError error = new GlobalError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, cryptoPricesTrackingException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GlobalError> handleUnhandledException(Exception exception) {
        GlobalError error = new GlobalError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}