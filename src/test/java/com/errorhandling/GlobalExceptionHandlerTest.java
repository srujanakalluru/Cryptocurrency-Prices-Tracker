package com.errorhandling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @Mock
    MessagingException messagingException;

    @Mock
    AuthenticationFailedException authenticationFailedException;

    @Mock
    MailAuthenticationException mailAuthenticationException;

    @Mock
    ConstraintViolationException constraintViolationException;

    @Mock
    MethodArgumentTypeMismatchException methodArgumentTypeMismatchException;

    @Mock
    CryptoPricesTrackingException cryptoPricesTrackingException;

    @Mock
    Exception exception;

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleMessagingException() {
        when(messagingException.getMessage()).thenReturn("messaging exception");
        ResponseEntity<GlobalError> responseEntity = assertDoesNotThrow(() -> globalExceptionHandler.handleMessagingException(messagingException));
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void handleAuthenticationFailedException() {
        when(authenticationFailedException.getMessage()).thenReturn("authentication failed exception");
        ResponseEntity<GlobalError> responseEntity = assertDoesNotThrow(() -> globalExceptionHandler.handleAuthenticationFailedException(authenticationFailedException));
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void handleMailAuthenticationException() {
        when(mailAuthenticationException.getMessage()).thenReturn("mail authentication failed exception");
        ResponseEntity<GlobalError> responseEntity = assertDoesNotThrow(() -> globalExceptionHandler.handleMailAuthenticationException(mailAuthenticationException));
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void handleConstraintViolationException() {
        when(constraintViolationException.getMessage()).thenReturn("constraint violation exception");
        ResponseEntity<GlobalError> responseEntity = assertDoesNotThrow(() -> globalExceptionHandler.handleConstraintViolationException(constraintViolationException));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void handleMethodArgumentTypeMismatchException() {
        when(methodArgumentTypeMismatchException.getMessage()).thenReturn("method argument type mismatch exception");
        ResponseEntity<GlobalError> responseEntity = assertDoesNotThrow(() -> globalExceptionHandler.handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void handleCryptoPricesTrackingException() {
        when(cryptoPricesTrackingException.getMessage()).thenReturn("crypto prices tracking exception");
        ResponseEntity<GlobalError> responseEntity = assertDoesNotThrow(() -> globalExceptionHandler.handleCryptoPricesTrackingException(cryptoPricesTrackingException));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testHandleUnhandledException() {
        when(exception.getMessage()).thenReturn("exception");
        ResponseEntity<GlobalError> responseEntity = assertDoesNotThrow(() -> globalExceptionHandler.handleUnhandledException(exception));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}