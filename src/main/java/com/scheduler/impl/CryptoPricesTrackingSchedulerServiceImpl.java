package com.scheduler.impl;

import com.configuration.SchedulerConfig;
import com.errorhandling.CryptoPricesTrackingException;
import com.scheduler.CryptoPricesTrackingSchedulerService;
import com.service.CryptoPricesTrackingService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CryptoPricesTrackingSchedulerServiceImpl implements CryptoPricesTrackingSchedulerService {
    private final SchedulerConfig schedulerConfig;
    private final CryptoPricesTrackingService service;

    ScheduledExecutorService localExecutor;
    ScheduledFuture<?> scheduledFuture;

    @Autowired
    public CryptoPricesTrackingSchedulerServiceImpl(SchedulerConfig schedulerConfig, CryptoPricesTrackingService service, ScheduledExecutorService localExecutor) {
        this.schedulerConfig = schedulerConfig;
        this.service = service;
        this.localExecutor = localExecutor;

    }

    /**
     * Service method to start the scheduler
     */
    @Override
    @SneakyThrows
    public void scheduleStart() {
        try {
            scheduledFuture = localExecutor.scheduleAtFixedRate(service::checkAndReportPrice, 0, schedulerConfig.fixedDelay(), TimeUnit.SECONDS);
            scheduledFuture.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CryptoPricesTrackingException("Scheduled task execution interrupted");
        }
    }

    /**
     * Service method to stop the scheduler
     *
     * @return boolean
     */
    @Override
    public boolean scheduleStop() {
        return scheduledFuture.cancel(true);
    }
}
