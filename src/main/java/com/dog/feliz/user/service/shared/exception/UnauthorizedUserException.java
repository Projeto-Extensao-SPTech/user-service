package com.dog.feliz.user.service.shared.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUserException extends RuntimeException {
    private final HttpStatus status;

    public UnauthorizedUserException(String message) {
        super(message);
        status = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
