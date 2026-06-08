package com.dog.feliz.user.service.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FairInterestConflictException extends RuntimeException {
    private final HttpStatus status = HttpStatus.CONFLICT;

    public FairInterestConflictException(String message) {
        super(message);
    }

}