package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.service.FairService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/feira")
public class FairController {

    FairService fairService;

    public FairController(FairService service) {
        this.fairService = service;
    }

    @PostMapping(name = "/cadastrar-feira", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FairEntity> createFair(
            @RequestBody FairRequestDto dto
    ) throws IOException {

        FairEntity fair = fairService.createFair(dto);

        return ResponseEntity.ok(fair);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FairResponseDto> getFair(@PathVariable Long id) {

        FairResponseDto fair = fairService.getFair(id);

        return ResponseEntity.ok(fair);
    }

}
