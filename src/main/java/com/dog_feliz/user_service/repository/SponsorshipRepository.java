package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.SponsorshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SponsorshipRepository extends JpaRepository<SponsorshipEntity, Long> {
    Optional<SponsorshipEntity> findBySponsorId(Long sponsorId);
}
