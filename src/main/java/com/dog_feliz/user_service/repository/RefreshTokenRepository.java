package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);
    @Modifying
    @Query("""
       UPDATE RefreshTokenEntity t
       SET t.revoked = true
       WHERE t.root.id = :rootId
    """)
    void revokeAllByRoot(@Param("rootId") Long rootId);
}
