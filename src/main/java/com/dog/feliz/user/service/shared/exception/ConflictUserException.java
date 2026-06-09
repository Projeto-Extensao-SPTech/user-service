package com.dog.feliz.user.service.shared.exception;

import org.springframework.http.HttpStatus;

public class ConflictUserException extends RuntimeException {
    private final HttpStatus status;

    public ConflictUserException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
