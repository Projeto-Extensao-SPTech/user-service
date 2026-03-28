package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.MailRequestDto;
import com.dog_feliz.user_service.controller.dto.MailResponseDto;
import com.dog_feliz.user_service.service.mail.factory.MailSenderFactory;
import com.dog_feliz.user_service.service.mail.strategy.MailSenderStrategy;
import com.dog_feliz.user_service.shared.exception.MailSenderException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mails")
public class MailController {

    private final MailSenderFactory mailSenderFactory;
    private static final Logger log = LoggerFactory.getLogger(MailController.class);

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

            log.info("[SEND_MAIL] Mail sent successfully to={} sender={}", to, mailSenderName);
        } catch (Exception e) {
            log.error("[SEND_MAIL] Error while sending mail to={} sender={} message={}", to, mailSenderName, e.getMessage(), e);
            throw new MailSenderException("Error sending mail: error=%s, cause=%s".formatted(e.getMessage(), e.getCause()));
        }
        return ResponseEntity.ok(new MailResponseDto(to));
    }
}