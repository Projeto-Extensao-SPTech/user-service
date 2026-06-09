package com.dog.feliz.user.service.shared.crypto.converter;

import jakarta.persistence.AttributeConverter;

public interface CryptoConverter<T> extends AttributeConverter<T, String> {
    String encrypt(T attribute);

    T decrypt(String attribute);
}
