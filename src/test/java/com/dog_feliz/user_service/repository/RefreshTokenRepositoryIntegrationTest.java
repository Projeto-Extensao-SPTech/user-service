package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.RefreshTokenEntity;
import com.dog_feliz.user_service.support.IntegrationTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenRepositoryIntegrationTest extends IntegrationTestBase {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("Dado refresh token salvo, quando findByTokenHash é chamado, deve encontrar o token")
    void givenSavedToken_whenFindByHash_thenReturnsToken() {
        RefreshTokenEntity entity = new RefreshTokenEntity(
                null,
                42L,
                "integration-hash",
                LocalDateTime.now().plusDays(7),
                false,
                null,
                null
        );
        RefreshTokenEntity saved = refreshTokenRepository.save(entity);
        saved.setRoot(saved);
        refreshTokenRepository.save(saved);

        Optional<RefreshTokenEntity> found = refreshTokenRepository.findByTokenHash("integration-hash");

        assertTrue(found.isPresent());
        assertEquals(42L, found.get().getUserId());
    }
}
