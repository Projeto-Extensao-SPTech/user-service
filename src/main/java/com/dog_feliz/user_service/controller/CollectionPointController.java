package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.CollectionPointRequestDto;
import com.dog_feliz.user_service.controller.dto.CollectionPointResponseDto;
import com.dog_feliz.user_service.service.CollectionPointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/collection-points")
public class CollectionPointController {

    @Autowired
    private CollectionPointService collectionPointService;

    // post
    @PostMapping
    public ResponseEntity<CollectionPointResponseDto> createCollectionPoint(@RequestBody @Valid CollectionPointRequestDto collectionPointRequest){
        CollectionPointResponseDto created = collectionPointService.addCollectionPoint(collectionPointRequest);
        return ResponseEntity.status(201).body(created);
    }
    // get todos
    @GetMapping
    public ResponseEntity<List<CollectionPointResponseDto>> getAllCollectionPoints(){
        List<CollectionPointResponseDto> collectionPoints = collectionPointService.getCollectionPoints();
        return collectionPoints.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(collectionPoints);
    }
    // get por id
    @GetMapping("/{id}")
    public ResponseEntity<CollectionPointResponseDto> getCollectionPointById(@PathVariable Long id){
        CollectionPointResponseDto collectionPoint = collectionPointService.getCollectionPointById(id);
        return ResponseEntity.ok(collectionPoint);
    }
    // put
    @PutMapping("/{id}")
    public ResponseEntity<CollectionPointResponseDto> updateCollectionPoint(@PathVariable Long id, @RequestBody @Valid CollectionPointRequestDto collectionPointRequest){
        CollectionPointResponseDto updated = collectionPointService.updateCollectionPoint(id, collectionPointRequest);
        return ResponseEntity.ok(updated);
    }
    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionPoint(@PathVariable Long id){
        collectionPointService.deleteCollectionPoint(id);
        return ResponseEntity.noContent().build();
    }
}
