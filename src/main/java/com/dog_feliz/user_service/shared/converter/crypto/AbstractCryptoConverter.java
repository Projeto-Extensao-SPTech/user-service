package com.dog_feliz.user_service.shared.converter.crypto;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public abstract class AbstractCryptoConverter<T> implements CryptoConverter<T> {

    protected static StandardPBEStringEncryptor encryptor;

    protected AbstractCryptoConverter(StandardPBEStringEncryptor encryptor) {
        AbstractCryptoConverter.encryptor = encryptor;
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

    public static void setEncryptor(StandardPBEStringEncryptor encryptor) {
        AbstractCryptoConverter.encryptor = encryptor;
    }

    protected abstract T convertFromString(String decrypted);
}

