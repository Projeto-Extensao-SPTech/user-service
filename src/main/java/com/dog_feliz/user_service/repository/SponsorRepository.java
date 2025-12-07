package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.SponsorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SponsorRepository extends JpaRepository<SponsorEntity, Long> {
    Optional<SponsorEntity> findByDocument(String document);
}
