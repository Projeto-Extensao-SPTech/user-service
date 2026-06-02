package com.dog_feliz.user_service.shared.crypto.converter;

import com.dog_feliz.user_service.shared.crypto.AesGcmEncryptor;

public abstract class AbstractCryptoConverter<T> implements CryptoConverter<T> {

    protected static AesGcmEncryptor encryptor;

    protected AbstractCryptoConverter(AesGcmEncryptor encryptor) {
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

    public static void setEncryptor(AesGcmEncryptor encryptor) {
        AbstractCryptoConverter.encryptor = encryptor;
    }

    protected abstract T convertFromString(String decrypted);
}

