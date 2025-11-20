package com.dog_feliz.user_service.service.mail.strategy;

import com.dog_feliz.user_service.controller.dto.MailRequestDto;
import jakarta.mail.MessagingException;

import java.util.List;

public interface MailSenderStrategy {
    void sendSimpleMail(MailRequestDto mailRequest, String to);
    void sendMailWithAttachment(MailRequestDto mailRequest, String to) throws MessagingException;
    void sendBulkMail(List<MailRequestDto> mailRequestDto);
}

