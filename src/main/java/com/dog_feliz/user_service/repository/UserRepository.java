package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByMailAddressHash(String mailAddressHash);
    Optional<UserEntity> findByPhone(String phone);
    List<UserEntity> findByReceiveNotificationsTrue();

    @Query("""
    SELECT TO_CHAR(u.createdAt, 'YYYY-MM') AS month,
    COUNT(u.id) AS total
    FROM UserEntity u
    GROUP BY TO_CHAR(u.createdAt, 'YYYY-MM')
    ORDER BY MIN(u.createdAt)
""")
    List<Object[]> getMonthlyUserRegistrations();
}
