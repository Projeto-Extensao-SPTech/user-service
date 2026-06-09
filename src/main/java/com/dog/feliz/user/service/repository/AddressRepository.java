package com.dog.feliz.user.service.repository;

import com.dog.feliz.user.service.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {}
