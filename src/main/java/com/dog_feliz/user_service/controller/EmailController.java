package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.EmailRequest;
import com.dog_feliz.user_service.controller.dto.EmailResponse;
import com.dog_feliz.user_service.service.mail.factory.MailSenderFactory;
import com.dog_feliz.user_service.service.mail.strategy.MailSenderStrategy;
import com.dog_feliz.user_service.shared.exception.MailSenderException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private MailSenderFactory mailSenderFactory;

    @PostMapping("/{mailSenderName}")
    private ResponseEntity<EmailResponse> sendEmail(
        @Valid @RequestBody EmailRequest emailRequest,
        @PathVariable String mailSenderName
    ) {
        MailSenderStrategy mailSender = mailSenderFactory.getSender(mailSenderName);
        try {
            if (emailRequest.getAttachment() != null && !emailRequest.getAttachment().isBlank()) {
                mailSender.sendEmailWithAttachment(emailRequest);
            } else {
                mailSender.sendSimpleEmail(emailRequest);
            }
        } catch (Exception e) {
            throw new MailSenderException("Error sending mail: error=%s, cause=%s".formatted(e.getMessage(), e.getCause()));
        }
        return ResponseEntity.ok(new EmailResponse(emailRequest.getTo()));
    }

}
