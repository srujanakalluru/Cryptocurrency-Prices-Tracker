package com.service.impl;

import com.configuration.EmailConfig;
import com.service.EmailService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailConfig emailConfig;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, EmailConfig emailConfig) {
        this.javaMailSender = javaMailSender;
        this.emailConfig = emailConfig;
    }

    /**
     * Method to send email alert with the alert price
     *
     * @param alertPrice alertPrice
     */
    @SneakyThrows
    public void sendEmailAlert(double alertPrice) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom(new InternetAddress(emailConfig.senderEmail(), emailConfig.senderName()));
        mimeMessageHelper.setTo(new InternetAddress(emailConfig.recipientEmail(), emailConfig.recipientName()));
        mimeMessageHelper.setText("New price is: " + alertPrice);
        mimeMessageHelper.setSubject("Price Change Alert");
        mimeMessageHelper.setPriority(0);
        javaMailSender.send(mimeMessage);
    }
}