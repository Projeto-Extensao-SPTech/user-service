package com.dog_feliz.user_service.shared.exception;

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
