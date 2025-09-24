package com.dog_feliz.user_service.converter.crypto;

import jakarta.persistence.Converter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

@Converter
public class StringCryptoConverter extends AbstractCryptoConverter<String> {
    protected StringCryptoConverter(StandardPBEStringEncryptor encryptor) {
        super(encryptor);
    }

    @Override
    protected String convertFromString(String decrypted) {
        return decrypted;
    }

    @Override
    public String convertToDatabaseColumn(String aString) {
        return aString == null ? null : encryptor.encrypt(aString);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return s == null ? null : encryptor.decrypt(s);
    }
}
