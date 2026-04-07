package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.service.FairService;
import com.dog_feliz.user_service.service.ValidationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/fairs")
public class FairController {

    private final FairService fairService;
    private final ValidationService validationService;

    public FairController(FairService fairService, ValidationService validationService) {
        this.fairService = fairService;
        this.validationService = validationService;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FairEntity> createFair(
            @RequestPart("fair") FairRequestDto dto,
            @RequestPart("image") MultipartFile[] image
    ) throws IOException {
        validationService.verifyIsAdminUser();
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
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        Path imagePath = Paths.get("uploads/fair/").resolve(fileName);

        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = Files.readAllBytes(imagePath);

        String contentType = Files.probeContentType(imagePath);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(imageBytes);
    }

    @GetMapping()
    public ResponseEntity<List<FairResponseDto>> getAllFairs() {
        List<FairResponseDto> fairs = fairService.getAllFair();
        return ResponseEntity.ok(fairs);
    }

    @GetMapping("/future")
    public Page<FairResponseDto> getFutureFairs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return fairService.getFutureFairs(page, size, sortBy);
    }

    @DeleteMapping("/{id}")
    public void deleteFair(@PathVariable Long id) {
        validationService.verifyIsAdminUser();
        fairService.deleteFair(id);
    }

    @PatchMapping("/{id}")
    public void insertInterest(@PathVariable Long id) {
        try {
            fairService.insertInterest(id);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível atualizar o campo de interesse pela feira");
        }
    }
}
