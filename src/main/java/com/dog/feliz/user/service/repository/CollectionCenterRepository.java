package com.dog.feliz.user.service.repository;

import com.dog.feliz.user.service.entity.CollectionCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionCenterRepository extends JpaRepository<CollectionCenterEntity, Integer> {}