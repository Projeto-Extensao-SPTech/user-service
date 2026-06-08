package com.dog_feliz.user_service.repository;
import com.dog_feliz.user_service.entity.UserFairInterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFairInterestRepository extends JpaRepository<UserFairInterestEntity, Long> {

    boolean existsByUserIdAndFairId(Long userId, Long fairId);

    long countByFairId(Long fairId);
}