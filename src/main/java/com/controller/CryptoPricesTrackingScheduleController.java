package com.controller;

import com.scheduler.CryptoPricesTrackingSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoPricesTrackingScheduleController {
    private final CryptoPricesTrackingSchedulerService cryptoPricesTrackingSchedulerService;

    @Autowired
    public CryptoPricesTrackingScheduleController(CryptoPricesTrackingSchedulerService cryptoPricesTrackingSchedulerService) {
        this.cryptoPricesTrackingSchedulerService = cryptoPricesTrackingSchedulerService;
    }

    /**
     * Starts the scheduler
     *
     * @return ResponseEntity<Void>
     */
    @GetMapping("start")
    ResponseEntity<Void> start() {
        cryptoPricesTrackingSchedulerService.scheduleStart();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Stops the scheduler
     *
     * @return ResponseEntity<Boolean>
     */
    @GetMapping("stop")
    ResponseEntity<Boolean> stop() {
        return ResponseEntity.ok(cryptoPricesTrackingSchedulerService.scheduleStop());
    }

}