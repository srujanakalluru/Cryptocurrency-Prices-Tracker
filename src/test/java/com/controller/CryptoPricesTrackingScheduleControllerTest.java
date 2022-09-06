package com.controller;

import com.errorhandling.CryptoPricesTrackingException;
import com.scheduler.CryptoPricesTrackingSchedulerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptoPricesTrackingScheduleControllerTest {

    @Mock
    CryptoPricesTrackingSchedulerService cryptoPricesTrackingSchedulerService;

    @Mock
    CryptoPricesTrackingException cryptoPricesTrackingException;

    @Mock
    ExecutionException executionException;

    @InjectMocks
    private CryptoPricesTrackingScheduleController cryptoPricesTrackingScheduleController;

    @Test
    void start_Success() {
        //given and when
        doNothing().when(cryptoPricesTrackingSchedulerService).scheduleStart();

        //then
        ResponseEntity<Void> response = assertDoesNotThrow(() -> cryptoPricesTrackingScheduleController.start());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void start_Failure() {
        //given and when
        when(cryptoPricesTrackingException.getMessage()).thenReturn("cryptoPricesTrackingException");
        doThrow(cryptoPricesTrackingException).when(cryptoPricesTrackingSchedulerService).scheduleStart();

        //then
        CryptoPricesTrackingException exception = Assertions.assertThrows(CryptoPricesTrackingException.class, () -> cryptoPricesTrackingScheduleController.start());
        assertNotNull(exception.getMessage());
    }

    @Test
    void stop_Success() {
        //given and when
        doReturn(true).when(cryptoPricesTrackingSchedulerService).scheduleStop();

        //then
        ResponseEntity<Boolean> response = assertDoesNotThrow(() -> cryptoPricesTrackingScheduleController.stop());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}