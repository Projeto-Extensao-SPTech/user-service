package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.entity.RefreshTokenEntity;
import com.dog.feliz.user.service.repository.RefreshTokenRepository;
import com.dog.feliz.user.service.shared.crypto.hash.StringHasher;
import com.dog.feliz.user.service.shared.exception.InvalidRefreshTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private StringHasher stringHasher;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
        refreshTokenService = new RefreshTokenService(refreshTokenRepository, stringHasher, 604800000);
        when(stringHasher.hash(anyString())).thenReturn("token-hash");
    }

    @Test
    @DisplayName("Dado token antigo nulo, quando generate é chamado, deve criar um novo refresh token")
    void givenNullOldToken_whenGenerate_thenCreatesNewToken() {
        RefreshTokenEntity saved = new RefreshTokenEntity(
                1L, 10L, "hash", LocalDateTime.now().plusDays(7), false, null, null
        );
        when(refreshTokenRepository.save(any())).thenReturn(saved);

        String token = refreshTokenService.generate(null, 10L);

        assertNotNull(token);
        verify(refreshTokenRepository, atLeast(1)).save(any());
    }

    @Test
    @DisplayName("Dado token revogado, quando generate é chamado, deve lançar InvalidRefreshTokenException")
    void givenRevokedToken_whenGenerate_thenThrowsInvalidRefreshToken() {
        RefreshTokenEntity revoked = new RefreshTokenEntity(
                1L, 10L, "hash", LocalDateTime.now().plusDays(1), true, null, null
        );
        RefreshTokenEntity root = new RefreshTokenEntity(
                2L, 10L, "root-hash", LocalDateTime.now().plusDays(7), false, null, null
        );
        revoked.setRoot(root);

        when(refreshTokenRepository.findByTokenHash("token-hash")).thenReturn(Optional.of(revoked));

        assertThrows(InvalidRefreshTokenException.class,
                () -> refreshTokenService.generate("revoked-token", 10L));
    }

    @Test
    @DisplayName("Dado token expirado, quando generate é chamado, deve lançar InvalidRefreshTokenException")
    void givenExpiredToken_whenGenerate_thenThrowsInvalidRefreshToken() {
        RefreshTokenEntity expired = new RefreshTokenEntity(
                1L, 10L, "hash", LocalDateTime.now().minusHours(1), false, null, null
        );
        when(refreshTokenRepository.findByTokenHash("token-hash")).thenReturn(Optional.of(expired));

        assertThrows(InvalidRefreshTokenException.class,
                () -> refreshTokenService.generate("expired-token", 10L));
    }

    @Test
    @DisplayName("Dado token válido, quando generate é chamado, deve rotacionar o token")
    void givenValidToken_whenGenerate_thenRotatesToken() {
        RefreshTokenEntity valid = new RefreshTokenEntity(
                1L, 10L, "hash", LocalDateTime.now().plusDays(7), false, null, null
        );
        when(refreshTokenRepository.findByTokenHash("token-hash")).thenReturn(Optional.of(valid));
        when(refreshTokenRepository.save(any())).thenAnswer(invocation -> {
            RefreshTokenEntity entity = invocation.getArgument(0);
            if (entity.getId() == null) {
                return new RefreshTokenEntity(
                        2L, entity.getUserId(), entity.getTokenHash(),
                        entity.getExpiresAt(), entity.isRevoked(), entity.getParent(), entity.getRoot()
                );
            }
            return entity;
        });

        String newToken = refreshTokenService.generate("valid-token", 10L);

        assertNotNull(newToken);
        verify(refreshTokenRepository, atLeast(2)).save(any());
    }

    @Test
    @DisplayName("Dado token desconhecido, quando revoke é chamado, deve lançar IllegalArgumentException")
    void givenUnknownToken_whenRevoke_thenThrowsIllegalArgument() {
        when(refreshTokenRepository.findByTokenHash(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> refreshTokenService.revoke("unknown"));
    }

    @Test
    @DisplayName("Dada família de tokens válida, quando revoke é chamado, deve revogar toda a família")
    void givenValidToken_whenRevoke_thenRevokesFamily() {
        RefreshTokenEntity root = new RefreshTokenEntity(
                5L, 10L, "root-hash", LocalDateTime.now().plusDays(7), false, null, null
        );
        RefreshTokenEntity token = new RefreshTokenEntity(
                1L, 10L, "hash", LocalDateTime.now().plusDays(7), false, null, root
        );
        when(refreshTokenRepository.findByTokenHash("token-hash")).thenReturn(Optional.of(token));

        refreshTokenService.revoke("valid-token");

        verify(refreshTokenRepository).revokeAllByRoot(5L);
    }
}
