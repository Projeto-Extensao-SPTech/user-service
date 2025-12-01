package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.FairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FairRepository extends JpaRepository<FairEntity, Long> {
    List<FairEntity> findByFairDateGreaterThan(LocalDate date);
}
