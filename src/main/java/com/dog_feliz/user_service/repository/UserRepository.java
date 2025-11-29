package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByMailAddress(String mailAddress);
    List<UserEntity> findByReceiveNotificationsTrue();
}
