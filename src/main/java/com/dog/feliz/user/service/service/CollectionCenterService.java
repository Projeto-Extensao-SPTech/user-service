package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.CollectionCenterResponseDto;
import com.dog.feliz.user.service.repository.CollectionCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionCenterService {

    @Autowired
    private CollectionCenterRepository repository;

    public List<CollectionCenterResponseDto> getAllCenters() {
        return repository.findAll()
                .stream()
                .map(CollectionCenterResponseDto::new)
                .toList();
    }
}