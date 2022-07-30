package com.service.impl;

import com.dto.EmailDetails;
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

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @SneakyThrows
    public void sendEmail(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom(new InternetAddress(details.getSenderEmail(), details.getSenderName()));
        mimeMessageHelper.setTo(new InternetAddress(details.getRecipientEmail(), details.getRecipientName()));
        mimeMessageHelper.setText(details.getMsgBody());
        mimeMessageHelper.setSubject(details.getSubject());
        mimeMessageHelper.setPriority(0);
        javaMailSender.send(mimeMessage);
    }
}