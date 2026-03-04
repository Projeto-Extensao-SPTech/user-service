package com.dog_feliz.user_service.shared.crypto.converter;

import jakarta.persistence.AttributeConverter;

public interface CryptoConverter<T> extends AttributeConverter<T, String> {
    public String encrypt(T attribute);
    public T decrypt(String attribute);
}
