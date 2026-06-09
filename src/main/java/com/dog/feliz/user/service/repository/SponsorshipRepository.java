package com.dog.feliz.user.service.repository;

import com.dog.feliz.user.service.entity.SponsorshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SponsorshipRepository extends JpaRepository<SponsorshipEntity, Long> {
    Optional<SponsorshipEntity> findBySponsorId(Long sponsorId);
}
