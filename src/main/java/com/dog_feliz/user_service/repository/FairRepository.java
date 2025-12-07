package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.controller.dto.DashboardFairKpiDto;
import com.dog_feliz.user_service.entity.FairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FairRepository extends JpaRepository<FairEntity, Long> {

    List<FairEntity> findByFairDateGreaterThan(LocalDate date);


    @Query("""
        SELECT FUNCTION('TO_CHAR', f.fairDate, 'YYYY-MM') AS month, SUM(f.interest) AS total
        FROM FairEntity f
        GROUP BY FUNCTION('TO_CHAR', f.fairDate, 'YYYY-MM')
        ORDER BY total DESC
    """)
    List<Object[]> findMonthWithMostInterestRaw();


    @Query("""
        SELECT f.address.street AS label, SUM(f.interest) AS total
        FROM FairEntity f
        GROUP BY f.address.street
        ORDER BY total DESC
    """)
    List<Object[]> findLocationWithMostInterestRaw();
}

