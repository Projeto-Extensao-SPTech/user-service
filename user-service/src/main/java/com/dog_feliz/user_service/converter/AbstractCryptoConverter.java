package com.dog_feliz.user_service.converter;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public abstract class AbstractCryptoConverter<T> implements CryptoConverter<T> {

    protected final StandardPBEStringEncryptor encryptor;

    protected AbstractCryptoConverter(StandardPBEStringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    public String encrypt(T attribute) {
        return attribute == null ? null : encryptor.encrypt(attribute.toString());
    }

    @Override
    public T decrypt(String dbData) {
        if (dbData == null) return null;
        return convertFromString(encryptor.decrypt(dbData));
    }

    protected abstract T convertFromString(String decrypted);
}

