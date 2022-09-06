package com.logging;

import com.scheduler.impl.CryptoPricesTrackingSchedulerServiceImpl;
import com.service.impl.CryptoPricesTrackingServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    @InjectMocks
    LoggingAspect loggingAspect;

    @Mock
    JoinPoint joinPoint;

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    CodeSignature signature;

    @Mock
    CryptoPricesTrackingServiceImpl cryptoPricesTrackingService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    Throwable ex;

    @Mock
    Exception exception;

    @Mock
    CryptoPricesTrackingSchedulerServiceImpl cryptoPricesTrackingSchedulerService;


    @Test
    void controllerPointCutTest() {
        assertDoesNotThrow(() -> loggingAspect.controllerPointCut());
    }

    @Test
    void repositoryPointCutTest() {
        assertDoesNotThrow(() -> loggingAspect.repositoryPointCut());
    }

    @Test
    void servicePointCutTest() {
        assertDoesNotThrow(() -> loggingAspect.servicePointCut());
    }

    @Test
    void schedulerStartPointCut() {
        assertDoesNotThrow(() -> loggingAspect.schedulerStartPointCut());
    }

    @Test
    void schedulerStopPointCut() {
        assertDoesNotThrow(() -> loggingAspect.schedulerStopPointCut());
    }

    @Test
    void externalServicePointCutTest() {
        assertDoesNotThrow(() -> loggingAspect.externalServicePointCut());
    }

    @Test
    void logBeforeSchedulerStartTest() {
        when(signature.getName()).thenReturn("scheduleStart");
        when(signature.getParameterNames()).thenReturn(null);
        when(joinPoint.getArgs()).thenReturn(null);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getTarget()).thenReturn(cryptoPricesTrackingService);
        assertDoesNotThrow(() -> loggingAspect.logBeforeSchedulerStart(joinPoint));
    }

    @Test
    void logBeforeSchedulerStopTest() {
        when(signature.getName()).thenReturn("stop");
        when(signature.getParameterNames()).thenReturn(null);
        when(joinPoint.getArgs()).thenReturn(null);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getTarget()).thenReturn(cryptoPricesTrackingService);
        assertDoesNotThrow(() -> loggingAspect.logBeforeSchedulerStop(joinPoint));
    }

    @Test
    void logAroundControllerTest() {
        when(signature.getName()).thenReturn("getPriceDetails");
        when(signature.getParameterNames()).thenReturn(null);
        when(proceedingJoinPoint.getArgs()).thenReturn(null);
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);
        when(proceedingJoinPoint.getTarget()).thenReturn(cryptoPricesTrackingService);
        assertDoesNotThrow(() -> loggingAspect.logAroundController(proceedingJoinPoint));
    }

    @Test
    void logAroundExternalServiceTest() {
        when(signature.getName()).thenReturn("getCryptoDetails");
        when(signature.getParameterNames()).thenReturn(null);
        when(proceedingJoinPoint.getArgs()).thenReturn(null);
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);
        when(proceedingJoinPoint.getTarget()).thenReturn(restTemplate);
        assertDoesNotThrow(() -> loggingAspect.logAroundExternalService(proceedingJoinPoint));
    }

    @Test
    void logAfterThrowingExceptionCallTest() {
        when(signature.getName()).thenReturn("start");
        when(signature.getParameterNames()).thenReturn(null);
        when(ex.getMessage()).thenReturn("Error occurred");
        when(joinPoint.getArgs()).thenReturn(null);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getTarget()).thenReturn(cryptoPricesTrackingSchedulerService);
        assertDoesNotThrow(() -> loggingAspect.logAfterThrowingExceptionCall(joinPoint, ex));
    }

    @Test
    void logAfterThrowingExceptionCallTest_WithCause() {
        when(signature.getName()).thenReturn("start");
        when(signature.getParameterNames()).thenReturn(null);
        when(ex.getMessage()).thenReturn(null);
        when(exception.getMessage()).thenReturn("Throwing exception!");
        when(ex.getCause()).thenReturn(exception);
        when(joinPoint.getArgs()).thenReturn(null);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getTarget()).thenReturn(cryptoPricesTrackingSchedulerService);
        assertDoesNotThrow(() -> loggingAspect.logAfterThrowingExceptionCall(joinPoint, ex));
    }
}
