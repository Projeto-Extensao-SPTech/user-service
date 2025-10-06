package com.dog_feliz.user_service.config;

import com.dog_feliz.user_service.shared.converter.crypto.DoubleCryptoConverter;
import com.dog_feliz.user_service.shared.converter.crypto.IntegerCryptoConverter;
import com.dog_feliz.user_service.shared.converter.crypto.StringCryptoConverter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterInitializer {
    public ConverterInitializer(StandardPBEStringEncryptor encryptor) {
        StringCryptoConverter.setEncryptor(encryptor);
        IntegerCryptoConverter.setEncryptor(encryptor);
        DoubleCryptoConverter.setEncryptor(encryptor);
    }
}
