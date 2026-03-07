package com.dog_feliz.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

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

    private LocalDateTime createdAt;

    public RefreshTokenEntity() {
    }

    public RefreshTokenEntity(Long id, Long userId, String tokenHash, LocalDateTime expiresAt, boolean revoked, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
