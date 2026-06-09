package com.dog.feliz.user.service.shared.crypto.converter;

import com.dog.feliz.user.service.shared.crypto.AesGcmEncryptor;
import jakarta.persistence.Converter;

@Converter
public class StringCryptoConverter extends AbstractCryptoConverter<String> {
    public StringCryptoConverter(AesGcmEncryptor encryptor) {
        super(encryptor);
    }

    @Override
    protected String convertFromString(String decrypted) {
        return decrypted;
    }

    @Override
    public String convertToDatabaseColumn(String input) {
        return input == null ? null : encryptor.encrypt(input);
    }

    @Override
    public String convertToEntityAttribute(String input) {
        return input == null ? null : encryptor.decrypt(input);
    }
}
