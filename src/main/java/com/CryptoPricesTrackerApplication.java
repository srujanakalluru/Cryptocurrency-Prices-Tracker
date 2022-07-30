package com;

import com.errorhandling.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class CryptoPricesTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptoPricesTrackerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("IST"));
    }

}
