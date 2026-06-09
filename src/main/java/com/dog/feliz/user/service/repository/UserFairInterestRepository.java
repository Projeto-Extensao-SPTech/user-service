package com.dog.feliz.user.service.repository;

import com.dog.feliz.user.service.entity.UserFairInterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFairInterestRepository extends JpaRepository<UserFairInterestEntity, Long> {

    boolean existsByUserIdAndFairId(Long userId, Long fairId);

    long countByFairId(Long fairId);
}