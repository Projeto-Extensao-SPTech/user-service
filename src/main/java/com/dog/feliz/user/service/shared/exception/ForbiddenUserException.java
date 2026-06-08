package com.dog.feliz.user.service.shared.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenUserException extends RuntimeException {
    private HttpStatus status;

    public ForbiddenUserException(String message) {
        super(message);
        status = HttpStatus.FORBIDDEN;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
