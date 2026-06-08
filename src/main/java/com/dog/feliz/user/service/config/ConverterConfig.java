package com.dog.feliz.user.service.config;

import com.dog.feliz.user.service.shared.crypto.AesGcmEncryptor;
import com.dog.feliz.user.service.shared.crypto.converter.DoubleCryptoConverter;
import com.dog.feliz.user.service.shared.crypto.converter.IntegerCryptoConverter;
import com.dog.feliz.user.service.shared.crypto.converter.StringCryptoConverter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {
    public ConverterConfig(AesGcmEncryptor encryptor) {
        StringCryptoConverter.setEncryptor(encryptor);
        IntegerCryptoConverter.setEncryptor(encryptor);
        DoubleCryptoConverter.setEncryptor(encryptor);
    }
}
