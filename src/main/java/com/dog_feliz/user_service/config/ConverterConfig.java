package com.dog_feliz.user_service.config;

import com.dog_feliz.user_service.shared.crypto.AesGcmEncryptor;
import com.dog_feliz.user_service.shared.crypto.converter.DoubleCryptoConverter;
import com.dog_feliz.user_service.shared.crypto.converter.IntegerCryptoConverter;
import com.dog_feliz.user_service.shared.crypto.converter.StringCryptoConverter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {
    public ConverterConfig(AesGcmEncryptor encryptor) {
        StringCryptoConverter.setEncryptor(encryptor);
        IntegerCryptoConverter.setEncryptor(encryptor);
        DoubleCryptoConverter.setEncryptor(encryptor);
    }
}
