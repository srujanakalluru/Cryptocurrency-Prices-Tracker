package com.service;

import com.configuration.EmailConfig;
import com.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    EmailConfig emailConfig;

    @Mock
    JavaMailSender javaMailSender;

    @Mock
    MimeMessage mimeMessage;


    @Test
    void testSendEmailAlert() {
        //given
        double alertPrice = 20000;

        //when
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailConfig.recipientEmail()).thenReturn("to@xyz.com");
        when(emailConfig.recipientName()).thenReturn("Jack Sparrow");
        when(emailConfig.senderEmail()).thenReturn("from@xyz.com");
        when(emailConfig.senderName()).thenReturn("John Smith");

        //then
        Assertions.assertDoesNotThrow(()->emailService.sendEmailAlert(alertPrice));
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));

    }
}
