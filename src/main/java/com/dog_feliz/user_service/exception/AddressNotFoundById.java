package com.dog_feliz.user_service.exception;

import org.springframework.http.HttpStatus;

public class AddressNotFoundById extends RuntimeException {
    private final HttpStatus status;

    public AddressNotFoundById(Long id) {
        super(String.format("Address not found by Id %d", id));
        this.status = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
