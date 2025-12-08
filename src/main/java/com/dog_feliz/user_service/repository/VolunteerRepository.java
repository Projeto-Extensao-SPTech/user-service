package com.dog_feliz.user_service.repository;


import com.dog_feliz.user_service.entity.VolunteerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerEntity, Long> {

    List<VolunteerEntity> findAllByAvailableDateIsNotNull();


    @Query("""
        SELECT FUNCTION('TO_CHAR', v.availableDate, 'YYYY-MM') AS month, COUNT(v.id) AS total
        FROM VolunteerEntity v
        GROUP BY FUNCTION('TO_CHAR', v.availableDate, 'YYYY-MM')
        ORDER BY month
    """)
    List<Object[]> getMonthlyRegistrationsRaw();
}
