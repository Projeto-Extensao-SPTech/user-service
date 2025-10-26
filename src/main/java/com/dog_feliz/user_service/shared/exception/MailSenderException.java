package com.dog_feliz.user_service.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;

public class MailSenderException extends MailException {
    private final HttpStatus status;

    public MailSenderException(String message) {
        super(message);
        status = HttpStatus.BAD_GATEWAY;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
