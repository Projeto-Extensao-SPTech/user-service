package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.FairEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FairRepository extends JpaRepository<FairEntity, Long> {

    Page<FairEntity> findByFairDateGreaterThan(LocalDate date, Pageable pageable);

    @Query("""
        SELECT FUNCTION('FORMATDATETIME', f.fairDate, 'MM')
        FROM FairEntity f
        GROUP BY FUNCTION('FORMATDATETIME', f.fairDate, 'MM')
        ORDER BY SUM(f.interest) DESC
    """)
    List<String> findMonthWithMostInterestRaw();



    @Query("""
                SELECT f.address.street AS label, SUM(f.interest) AS total
                FROM FairEntity f
                GROUP BY f.address.street
                ORDER BY total DESC
            """)
    List<Object[]> findLocationWithMostInterestRaw();
}

