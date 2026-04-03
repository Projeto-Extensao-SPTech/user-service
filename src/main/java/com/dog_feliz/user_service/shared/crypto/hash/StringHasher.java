package com.dog_feliz.user_service.shared.crypto.hash;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class StringHasher {

    @Value("${security.hash.secret-key}")
    private String secret;

    public String hash(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(keySpec);

            byte[] hashBytes = mac.doFinal(
                    value.toLowerCase().trim().getBytes(StandardCharsets.UTF_8)
            );

            return bytesToHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash da string", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}