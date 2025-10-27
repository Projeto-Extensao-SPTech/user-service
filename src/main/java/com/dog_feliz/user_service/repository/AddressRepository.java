package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {}
