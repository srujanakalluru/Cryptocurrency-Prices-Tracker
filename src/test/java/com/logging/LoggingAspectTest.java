package com.logging;

import com.service.impl.CryptoPricesTrackingServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    @InjectMocks
    LoggingAspect loggingAspect;

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    CodeSignature signature;

    @Mock
    CryptoPricesTrackingServiceImpl cryptoPricesTrackingService;


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
    void logAroundController() {
        when(signature.getName()).thenReturn("getPriceDetails");
        when(signature.getParameterNames()).thenReturn(null);
        when(proceedingJoinPoint.getArgs()).thenReturn(null);
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);
        when(proceedingJoinPoint.getTarget()).thenReturn(cryptoPricesTrackingService);
        assertDoesNotThrow(() -> loggingAspect.logAroundController(proceedingJoinPoint));
    }

}