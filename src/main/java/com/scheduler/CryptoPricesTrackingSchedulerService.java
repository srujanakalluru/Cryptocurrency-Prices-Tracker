package com.scheduler;


public interface CryptoPricesTrackingSchedulerService {
    void scheduleStart();

    boolean scheduleStop();
}
