package com.dog_feliz.user_service.service.mail.strategy;

import com.dog_feliz.user_service.controller.dto.EmailRequest;
import jakarta.mail.MessagingException;

public interface MailSenderStrategy {
    void sendSimpleEmail(EmailRequest emailRequest);
    void sendEmailWithAttachment(EmailRequest emailRequest) throws MessagingException;
}
