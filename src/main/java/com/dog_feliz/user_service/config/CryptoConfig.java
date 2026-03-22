package com.dog_feliz.user_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class CryptoConfig {

    @Value("${aes.encryptor.secret-key}")
    private String base64Key;

    @Bean
    public SecretKey aesSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        if (decodedKey.length != 32) {
            throw new IllegalArgumentException("AES key must be 256 bits (32 bytes)");
        }

        return new SecretKeySpec(decodedKey, "AES");
    }
}