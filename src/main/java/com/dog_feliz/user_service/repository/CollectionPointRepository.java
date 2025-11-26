package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.CollectionPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionPointRepository extends JpaRepository<CollectionPointEntity, Long> {
}
