package com.dog_feliz.user_service.shared.exception;

import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends RuntimeException {
    private HttpStatus status;

    public InvalidRefreshTokenException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
