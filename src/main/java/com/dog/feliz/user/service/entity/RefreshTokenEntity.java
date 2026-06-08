package com.dog.feliz.user.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_token_tb")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String tokenHash;

    private LocalDateTime expiresAt;

    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RefreshTokenEntity parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_id")
    private RefreshTokenEntity root;

    private LocalDateTime createdAt = LocalDateTime.now();

    public RefreshTokenEntity() {
    }

    public RefreshTokenEntity(
            Long id,
            Long userId,
            String tokenHash,
            LocalDateTime expiresAt,
            boolean revoked,
            RefreshTokenEntity parent,
            RefreshTokenEntity root
    ) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.parent = parent;
        this.root = root;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public RefreshTokenEntity getParent() {
        return parent;
    }

    public RefreshTokenEntity getRoot() {
        return root;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setRoot(RefreshTokenEntity refreshTokenEntity) {
        this.root = refreshTokenEntity;
    }

    private void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    public void revoke() {
        if (this.isRevoked()) {
            throw new IllegalStateException("Refresh token is already revoked");
        }
        setRevoked(true);
    }

    public static String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public Boolean isExpired() {
        return this.getExpiresAt().isBefore(LocalDateTime.now());
    }
}
