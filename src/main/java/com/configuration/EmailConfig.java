package com.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${application.mail.recipient}")
    private String recipientEmail;

    @Value("${application.mail.sender}")
    private String senderEmail;

    @Bean
    public String recipientEmail() {
        return recipientEmail;
    }

    @Bean
    public String senderEmail() {
        return senderEmail;
    }

}
