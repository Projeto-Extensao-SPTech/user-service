package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.CollectionCenterResponseDto;
import com.dog_feliz.user_service.service.CollectionCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/collection-centers")
public class CollectionCenterController {

    @Autowired
    private CollectionCenterService service;

    @GetMapping
    public ResponseEntity<List<CollectionCenterResponseDto>> listAll() {
        return ResponseEntity.ok(service.getAllCenters());
    }
}