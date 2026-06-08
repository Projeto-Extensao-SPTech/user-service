package com.dog.feliz.user.service.repository;

import com.dog.feliz.user.service.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
    List<DonationEntity> findByUserId(Integer userId);
}
