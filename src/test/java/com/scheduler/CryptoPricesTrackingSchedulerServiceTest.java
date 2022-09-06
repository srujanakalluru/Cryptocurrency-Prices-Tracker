package com.scheduler;

import com.configuration.SchedulerConfig;
import com.errorhandling.CryptoPricesTrackingException;
import com.scheduler.impl.CryptoPricesTrackingSchedulerServiceImpl;
import com.service.CryptoPricesTrackingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.AuthenticationFailedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoPricesTrackingSchedulerServiceTest {
    @Mock
    private SchedulerConfig schedulerConfig;

    @Mock
    private CryptoPricesTrackingService cryptoPricesTrackingService;

    @Mock
    ScheduledExecutorService localExecutor;

    @Mock
    ExecutionException executionException;

    @Mock
    InterruptedException interruptedException;

    @Mock
    AuthenticationFailedException authenticationFailedException;

    @Mock
    ScheduledFuture scheduledFuture;

    @InjectMocks
    private CryptoPricesTrackingSchedulerServiceImpl cryptoPricesTrackingSchedulerServiceImpl;

    @Test
    void scheduleStart_Success() {
        when(localExecutor.scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any(TimeUnit.class))).thenReturn(scheduledFuture);
        assertDoesNotThrow(() -> cryptoPricesTrackingSchedulerServiceImpl.scheduleStart());
    }

    @Test
    void scheduleStart_ThrowsExecutionException() throws ExecutionException, InterruptedException {
        when(executionException.getCause()).thenReturn(authenticationFailedException);
        when(localExecutor.scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any(TimeUnit.class))).thenReturn(scheduledFuture);
        when(scheduledFuture.get()).thenThrow(executionException);
        assertThrows(AuthenticationFailedException.class, () -> cryptoPricesTrackingSchedulerServiceImpl.scheduleStart());
    }

    @Test
    void scheduleStart_ThrowsInterruptedException() throws ExecutionException, InterruptedException {
        when(localExecutor.scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any(TimeUnit.class))).thenReturn(scheduledFuture);
        when(scheduledFuture.get()).thenThrow(interruptedException);
        assertThrows(CryptoPricesTrackingException.class, () -> cryptoPricesTrackingSchedulerServiceImpl.scheduleStart());
    }

    @Test
    void scheduleStop_success() {
        //given and when
        when(localExecutor.scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any(TimeUnit.class))).thenReturn(scheduledFuture);
        cryptoPricesTrackingSchedulerServiceImpl.scheduleStart();
        when(scheduledFuture.cancel(true)).thenReturn(true);

        //then
        assertDoesNotThrow(() -> cryptoPricesTrackingSchedulerServiceImpl.scheduleStop());
    }
}