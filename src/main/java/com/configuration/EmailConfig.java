package com.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${application.mail.recipient.name}")
    private String recipientName;

    @Value("${application.mail.recipient.email}")
    private String recipientEmail;

    @Value("${application.mail.sender.name}")
    private String senderName;

    @Value("${application.mail.sender.email}")
    private String senderEmail;

    @Bean
    public String recipientName() {
        return recipientName;
    }

    @Bean
    public String recipientEmail() {
        return recipientEmail;
    }

    @Bean
    public String senderName() {
        return senderName;
    }

    @Bean
    public String senderEmail() {
        return senderEmail;
    }

}
