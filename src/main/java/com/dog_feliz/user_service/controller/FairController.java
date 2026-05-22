package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.service.FairService;
import com.dog_feliz.user_service.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

        log.info("[CREATE_FAIR] Fair created successfully");
        return ResponseEntity.ok(fair);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FairResponseDto> getFair(@PathVariable Long id) {

        FairResponseDto fair = toResponse(fairService.getFair(id));

        log.info("[GET_FAIR] Fair fetched successfully fairId={}", id);
        return ResponseEntity.ok(fair);
    }

    @GetMapping(
            value = "/images/{fileName}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        Path imagePath = Paths.get("uploads/fair/").resolve(fileName);

        if (!Files.exists(imagePath)) {
            log.warn("[GET_FAIR_IMAGE] Image not found fileName={}", fileName);
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = Files.readAllBytes(imagePath);

        String contentType = Files.probeContentType(imagePath);

        log.info("[GET_FAIR_IMAGE] Image fetched successfully fileName={}", fileName);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(imageBytes);
    }

    @GetMapping()
    public ResponseEntity<List<FairResponseDto>> getAllFairs() {
        List<FairResponseDto> fairs = fairService.getAllFair();
        log.info("[GET_ALL_FAIRS] Fairs fetched successfully total={}", fairs.size());
        return ResponseEntity.ok(fairs);
    }

    @GetMapping("/future")
    public Page<FairResponseDto> getFutureFairs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<FairResponseDto> fairs = fairService.getFutureFairs(page, size, sortBy);

        log.info("[GET_FUTURE_FAIRS] Future fairs fetched successfully page={} size={} sortBy={} totalElements={}",
                page, size, sortBy, fairs.getTotalElements());
        return fairs;
    }

    @DeleteMapping("/{id}")
    public void deleteFair(@PathVariable Long id) {
        validationService.verifyIsAdminUser();
        fairService.deleteFair(id);
        log.info("[DELETE_FAIR] Fair deleted successfully fairId={}", id);
    }

    @PatchMapping("/{id}")
    public void insertInterest(@PathVariable Long id) {
        try {
            fairService.insertInterest(id);
            log.info("[INSERT_INTEREST_FAIR] Interest updated successfully fairId={}", id);
        } catch (Exception e) {
            log.error("[INSERT_INTEREST_FAIR] Error while updating interest fairId={} message={}", id, e.getMessage(), e);
            throw new RuntimeException("Não foi possível atualizar o campo de interesse pela feira");
        }
    }

    private FairResponseDto toResponse(FairEntity fair) {
        return new FairResponseDto(fair);
    }
}