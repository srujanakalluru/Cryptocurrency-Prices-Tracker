package com.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Configuration
public class SchedulerConfig {
    @Value("${alert.interval.seconds}")
    private long fixedDelay;

    @Bean
    public long fixedDelay() {
        return this.fixedDelay;
    }

    @Bean
    public ScheduledExecutorService localExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

}
