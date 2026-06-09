package com.dog.feliz.user.service.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidRecoveryCodeException extends RuntimeException {
    private final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

    public InvalidRecoveryCodeException(String message) {
        super(message);
    }

}