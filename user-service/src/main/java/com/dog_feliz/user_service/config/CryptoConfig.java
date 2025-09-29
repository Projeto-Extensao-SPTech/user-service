package com.dog_feliz.user_service.config;

import com.dog_feliz.user_service.converter.crypto.StringCryptoConverter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoConfig {

    @Value("${jasypt.encryptor.password}")
    private String password;

    @Bean
    public StandardPBEStringEncryptor encryptor() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndTripleDES");
        standardPBEStringEncryptor.setPassword(password);
        return standardPBEStringEncryptor;
    }

    @Bean
    public StringCryptoConverter stringCryptoConverter() {
      return new StringCryptoConverter(encryptor());
    }
}
