package com.example.enotes.service.impl;

import com.example.enotes.dto.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("{spring.mail.username}")
    private String mailFrom;

    public void sendEmail(EmailRequest emailRequest) throws Exception {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message);
        helper.setFrom(mailFrom, emailRequest.getTitle());
        helper.setTo(emailRequest.getTo());
        helper.setText(emailRequest.getText(),true);
        helper.setSubject(emailRequest.getSubject());

        mailSender.send(message);
    }
}