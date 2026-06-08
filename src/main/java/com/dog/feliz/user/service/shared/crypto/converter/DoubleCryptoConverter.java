package com.dog.feliz.user.service.shared.crypto.converter;

import com.dog.feliz.user.service.shared.crypto.AesGcmEncryptor;
import jakarta.persistence.Converter;

@Converter
public class DoubleCryptoConverter extends AbstractCryptoConverter<Double> {
    public DoubleCryptoConverter(AesGcmEncryptor encryptor) {
        super(encryptor);
    }

    @Override
    protected Double convertFromString(String decrypted) {
        return decrypted == null ? null : Double.parseDouble(decrypted);
    }

    @Override
    public String convertToDatabaseColumn(Double aDouble) {
        return aDouble == null ? null : encryptor.encrypt(aDouble.toString());
    }

    @Override
    public Double convertToEntityAttribute(String s) {
        return s == null ? null : convertFromString(encryptor.decrypt(s));
    }
}
