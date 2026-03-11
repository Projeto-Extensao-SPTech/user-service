package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.entity.RefreshTokenEntity;
import com.dog_feliz.user_service.repository.RefreshTokenRepository;
import com.dog_feliz.user_service.shared.exception.InvalidRefreshTokenException;
import com.dog_feliz.user_service.shared.crypto.hash.StringHasher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final StringHasher stringHasher;
    private final Integer REFRESH_TOKEN_EXPIRATION_TIME;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            StringHasher stringHasher,
            @Value("${security.refresh-token.expiration-time}") Integer REFRESH_TOKEN_EXPIRATION_TIME
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.stringHasher = stringHasher;
        this.REFRESH_TOKEN_EXPIRATION_TIME = REFRESH_TOKEN_EXPIRATION_TIME;
    }

    @Transactional
    public String generate(String oldRefreshToken, Long userId) {
        if (oldRefreshToken == null) {
            return create(
                    userId,
                    null,
                    null
            );
        }
        return refresh(oldRefreshToken);
    }

    private String refresh(String actualRefreshToken) {
        RefreshTokenEntity token = findRefreshTokenByTokenHash(actualRefreshToken);
        if (token.isRevoked()) {
            revokeFamily(token.getRoot());
            throw new InvalidRefreshTokenException("Refresh token reuse detected");
        }

        if (token.isExpired()) {
            throw new InvalidRefreshTokenException("Refresh token expired");
        }

        token.revoke();
        refreshTokenRepository.save(token);

        return create(
                token.getUserId(),
                token,
                token.getRoot()
        );
    }

    private String create(Long userId, RefreshTokenEntity parent, RefreshTokenEntity root) {
        String refreshToken = RefreshTokenEntity.generateRefreshToken();
        LocalDateTime expiration =
                root == null
                        ? LocalDateTime.now().plus(REFRESH_TOKEN_EXPIRATION_TIME, ChronoUnit.MILLIS)
                        : root.getExpiresAt();
        RefreshTokenEntity entity = new RefreshTokenEntity(
                null,
                userId,
                stringHasher.hash(refreshToken),
                expiration,
                false,
                parent,
                root
        );
        RefreshTokenEntity saved = refreshTokenRepository.save(entity);

        if (root == null) {
            saved.setRoot(saved);
        }

        refreshTokenRepository.save(saved);
        return refreshToken;
    }

    public void revoke(String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = findRefreshTokenByTokenHash(refreshToken);
        revokeFamily(refreshTokenEntity.getRoot());
    }

    private void revokeFamily(RefreshTokenEntity root) {
        refreshTokenRepository.revokeAllByRoot(root.getId());
    }

    private RefreshTokenEntity findRefreshTokenByTokenHash(String refreshToken) {
        return refreshTokenRepository
                .findByTokenHash(stringHasher.hash(refreshToken))
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token to revoke"));

    }
}