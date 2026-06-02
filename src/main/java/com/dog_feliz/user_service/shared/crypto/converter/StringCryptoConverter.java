package com.dog_feliz.user_service.shared.crypto.converter;

import com.dog_feliz.user_service.shared.crypto.AesGcmEncryptor;
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
    public String convertToDatabaseColumn(String aString) {
        return aString == null ? null : encryptor.encrypt(aString);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return s == null ? null : encryptor.decrypt(s);
    }
}
