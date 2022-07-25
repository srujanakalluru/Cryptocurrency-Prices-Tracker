package com.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlertConfig {

    @Value("${alert.price.min}")
    private double alertPriceMin;

    @Value("${alert.price.max}")
    private double alertPriceMax;

    @Value("${alert.coin.id}")
    private String coinId;

    @Value("${alert.coin.currency}")
    private String valueCurrency;

    @Value("${alert.interval.seconds}")
    private int alertInterval;

    @Bean
    public double alertPriceMin() {
        return alertPriceMin;
    }

    @Bean
    public double alertPriceMax() {
        return alertPriceMax;
    }

    @Bean
    public String coinId() {
        return coinId;
    }

    @Bean
    public String valueCurrency() {
        return valueCurrency;
    }

    @Bean
    public int alertInterval() {
        return alertInterval;
    }
}
