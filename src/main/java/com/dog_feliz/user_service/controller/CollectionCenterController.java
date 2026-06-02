package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.CollectionCenterResponseDto;
import com.dog_feliz.user_service.service.CollectionCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/collection-centers")
public class CollectionCenterController {

    private final CollectionCenterService service;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public CollectionCenterController(CollectionCenterService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CollectionCenterResponseDto>> listAll() {
        List<CollectionCenterResponseDto> centers = service.getAllCenters();
        log.info("[LIST_COLLECTION_CENTER] List all collection centers, response={}", centers);
        return ResponseEntity.ok(centers);
    }
}