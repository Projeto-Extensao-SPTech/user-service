package com.dog_feliz.user_service.config;

import com.dog_feliz.user_service.shared.crypto.DoubleCryptoConverter;
import com.dog_feliz.user_service.shared.crypto.IntegerCryptoConverter;
import com.dog_feliz.user_service.shared.crypto.StringCryptoConverter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {
    public ConverterConfig(StandardPBEStringEncryptor encryptor) {
        StringCryptoConverter.setEncryptor(encryptor);
        IntegerCryptoConverter.setEncryptor(encryptor);
        DoubleCryptoConverter.setEncryptor(encryptor);
    }
}
