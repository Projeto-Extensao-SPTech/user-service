package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.CollectionCenterResponseDto;
import com.dog_feliz.user_service.repository.CollectionCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionCenterService {

    @Autowired
    private CollectionCenterRepository repository;

    public List<CollectionCenterResponseDto> getAllCenters() {
        // Busca todas as entidades e transforma cada uma em DTO
        return repository.findAll()
                .stream()
                .map(CollectionCenterResponseDto::new)
                .toList();
    }
}