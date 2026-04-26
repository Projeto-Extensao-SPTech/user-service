package com.dog_feliz.user_service.shared.crypto.converter;

import com.dog_feliz.user_service.shared.crypto.AesGcmEncryptor;
import jakarta.persistence.Converter;

@Converter
public class IntegerCryptoConverter extends AbstractCryptoConverter<Integer> {
    public IntegerCryptoConverter(AesGcmEncryptor encryptor) {
        super(encryptor);
    }

    @Override
    protected Integer convertFromString(String decrypted) {
        return decrypted == null ? null : Integer.parseInt(decrypted);
    }

    @Override
    public String convertToDatabaseColumn(Integer aInteger) {
        return aInteger == null ? null : encryptor.encrypt(aInteger.toString());
    }

    @Override
    public Integer convertToEntityAttribute(String s) {
        return s == null ? null : convertFromString(encryptor.decrypt(s));
    }
}
