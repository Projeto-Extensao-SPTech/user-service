package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.EmailRequest;
import com.dog_feliz.user_service.controller.dto.EmailResponse;
import com.dog_feliz.user_service.service.mail.factory.MailSenderFactory;
import com.dog_feliz.user_service.service.mail.strategy.MailSenderStrategy;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private MailSenderFactory mailSenderFactory;

    @PostMapping("/{mailSenderName}")
    private ResponseEntity<EmailResponse> sendEmail(
        @Valid @RequestBody EmailRequest emailRequest,
        @PathVariable String mailSenderName
    ) throws MessagingException {
        MailSenderStrategy mailSender = mailSenderFactory.getSender(mailSenderName);
        if (emailRequest.getAttachment() != null && !emailRequest.getAttachment().isBlank()) {
            mailSender.sendEmailWithAttachment(emailRequest);
        } else {
            mailSender.sendSimpleEmail(emailRequest);
        }
        return ResponseEntity.ok(new EmailResponse(emailRequest.getTo()));
    }

}
