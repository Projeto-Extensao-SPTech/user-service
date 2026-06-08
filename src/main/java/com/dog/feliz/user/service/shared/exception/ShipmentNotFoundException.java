package com.dog.feliz.user.service.shared.exception;

import org.springframework.http.HttpStatus;

public class ShipmentNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public ShipmentNotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
