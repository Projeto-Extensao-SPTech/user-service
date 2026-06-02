package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
    List<DonationEntity> findByUserId(Integer userId);
}
