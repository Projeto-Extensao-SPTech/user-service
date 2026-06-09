package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.entity.RefreshTokenEntity;
import com.dog.feliz.user.service.repository.RefreshTokenRepository;
import com.dog.feliz.user.service.shared.exception.InvalidRefreshTokenException;
import com.dog.feliz.user.service.shared.crypto.hash.StringHasher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final StringHasher stringHasher;

    private final Integer refreshTokenExpirationTime;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            StringHasher stringHasher,
            @Value("${security.refresh-token.expiration-time}") Integer refreshTokenExpirationTime
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.stringHasher = stringHasher;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
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
                        ? LocalDateTime.now().plus(refreshTokenExpirationTime, ChronoUnit.MILLIS)
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