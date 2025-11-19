package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.service.FairService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/feiras")
public class FairController {

    FairService fairService;

    public FairController(FairService service) {
        this.fairService = service;
    }

    @PostMapping(value = "/cadastrar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FairEntity> createFair(
            @RequestPart("fair") FairRequestDto dto,
            @RequestPart("imagem") MultipartFile[] image
    ) throws IOException {

        dto.setImage(List.of(image));
        FairEntity fair = fairService.createFair(dto);

        return ResponseEntity.ok(fair);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FairResponseDto> getFair(@PathVariable Long id) {

        FairResponseDto fair = fairService.getFair(id);

        return ResponseEntity.ok(fair);
    }

    @GetMapping(
            value = "/images/{fileName}",
            produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }
    )
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {

        Path imagePath = Paths.get("uploads/fair/").resolve(fileName);

        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = Files.readAllBytes(imagePath);

        return ResponseEntity.ok().body(imageBytes);
    }
}
