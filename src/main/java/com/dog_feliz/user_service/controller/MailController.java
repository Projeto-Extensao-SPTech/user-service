package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.MailRequestDto;
import com.dog_feliz.user_service.controller.dto.MailResponseDto;
import com.dog_feliz.user_service.service.mail.factory.MailSenderFactory;
import com.dog_feliz.user_service.service.mail.strategy.MailSenderStrategy;
import com.dog_feliz.user_service.shared.exception.MailSenderException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mails")
public class MailController {

    private final MailSenderFactory mailSenderFactory;

    public MailController(MailSenderFactory mailSenderFactory) {
        this.mailSenderFactory = mailSenderFactory;
    }

    @PostMapping("/{mailSenderName}/{to}")
    private ResponseEntity<MailResponseDto> sendMail(
        @Valid @RequestBody MailRequestDto mailRequest,
        @PathVariable String mailSenderName,
        @PathVariable String to
    ) {
        MailSenderStrategy mailSender = mailSenderFactory.getSender(mailSenderName);
        try {
            if (mailRequest.getAttachment() != null && !mailRequest.getAttachment().isBlank()) {
                mailSender.sendMailWithAttachment(mailRequest, to);
            } else {
                mailSender.sendSimpleMail(mailRequest, to);
            }
        } catch (Exception e) {
            throw new MailSenderException("Error sending mail: error=%s, cause=%s".formatted(e.getMessage(), e.getCause()));
        }
        return ResponseEntity.ok(new MailResponseDto(to));
    }

}
