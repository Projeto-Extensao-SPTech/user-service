package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByTokenHashAndRevokedFalse(String tokenHash);
    List<RefreshTokenEntity> findAllByUserIdAndRevokedFalse(Long userId);
}
