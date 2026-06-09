package com.dog.feliz.user.service.shared.exception;

import org.springframework.http.HttpStatus;

public class VolunteerNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public VolunteerNotFoundException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
