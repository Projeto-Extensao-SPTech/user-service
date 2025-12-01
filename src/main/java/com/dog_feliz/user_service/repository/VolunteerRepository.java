package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.VolunteerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<VolunteerEntity, Long> {

}
