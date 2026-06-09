package com.dog.feliz.user.service.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;

public class MailSenderException extends MailException {
    private final HttpStatus status;

    public MailSenderException(String message) {
        super(message);
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
