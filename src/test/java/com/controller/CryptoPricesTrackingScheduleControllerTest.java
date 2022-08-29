package com.controller;

import com.scheduler.CryptoPricesTrackingSchedulerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CryptoPricesTrackingScheduleControllerTest {

    @Mock
    CryptoPricesTrackingSchedulerService cryptoPricesTrackingSchedulerService;

    @InjectMocks
    private CryptoPricesTrackingScheduleController cryptoPricesTrackingScheduleController;

    @Test
    void start_Success() {
        //given and when
        doNothing().when(cryptoPricesTrackingSchedulerService).scheduleStart();

        //then
        ResponseEntity<Void> response = Assertions.assertDoesNotThrow(() -> cryptoPricesTrackingScheduleController.start());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
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