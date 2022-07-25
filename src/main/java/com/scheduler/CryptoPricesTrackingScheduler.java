package com.scheduler;

import com.service.CryptoPricesTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@EnableScheduling
@Component
public class CryptoPricesTrackingScheduler {
    private final CryptoPricesTrackingService service;

    @Autowired
    public CryptoPricesTrackingScheduler(CryptoPricesTrackingService service) {
        this.service = service;
    }

    @Scheduled(fixedDelayString = "${alert.interval.seconds}", timeUnit = TimeUnit.SECONDS)
    public void checkAndReportPrice() {
        service.checkAndReportPrice();
    }

}