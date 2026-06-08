package com.dog.feliz.user.service.repository;

import com.dog.feliz.user.service.entity.user.UserEntity;
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

    Boolean existsByPhone(String phone);

    @Query("""
    SELECT TO_CHAR(u.createdAt, 'YYYY-MM') AS month,
    COUNT(u.id) AS total
    FROM UserEntity u
    GROUP BY TO_CHAR(u.createdAt, 'YYYY-MM')
    ORDER BY MIN(u.createdAt)
""")
    List<Object[]> getMonthlyUserRegistrations();
}
