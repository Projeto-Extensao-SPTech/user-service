package com.dog.feliz.user.service.shared.exception;

import org.springframework.http.HttpStatus;

public class SponsorshipNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public SponsorshipNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
