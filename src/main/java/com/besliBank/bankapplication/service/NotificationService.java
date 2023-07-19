package com.besliBank.bankapplication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    private final EmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "information",
            groupId = "group-id"
    )
    public void information(String message){
        logger.info(String.format(message+ " "+ LocalDateTime.now()));
    }
    @KafkaListener(
            topics = "transfer-notification",
            groupId = "group-id")
    public void consume(String message){
        String[] parts = message.split(",");
        String to = parts[0];
        String subject = parts[1];
        String body = parts[2];
        emailService.sendEmail(to, subject, body);

    }
}
