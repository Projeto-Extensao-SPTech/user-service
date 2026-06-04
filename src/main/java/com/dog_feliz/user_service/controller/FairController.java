package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.controller.dto.PageResponseDto;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.service.FairService;
import com.dog_feliz.user_service.service.ValidationService;
import com.dog_feliz.user_service.service.storage.S3StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/fairs")
public class FairController {

    private final FairService fairService;
    private final ValidationService validationService;
    private final S3StorageService storageService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public FairController(FairService fairService, ValidationService validationService, S3StorageService storageService) {
        this.fairService = fairService;
        this.validationService = validationService;
        this.storageService = storageService;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FairEntity> createFair(
            @RequestPart("fair") FairRequestDto dto,
            @RequestPart("image") MultipartFile[] image
    ) {
        validationService.verifyIsAdminUser();
        dto.setImages(List.of(image));
        FairEntity fair = fairService.createFair(dto);

        log.info("[CREATE_FAIR] Fair created successfully fairId={}", fair.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(fair);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FairResponseDto> getFair(@PathVariable Long id) {

        FairResponseDto fair = toResponse(fairService.getFair(id));

        log.info("[GET_FAIR] Fair fetched successfully fairId={}", id);
        return ResponseEntity.ok(fair);
    }

    @GetMapping("/images")
    public ResponseEntity<Void> getImage(@RequestParam String key) {
        String presignedUrl = storageService.getPresignedUrl(key, Duration.ofMinutes(15));

        log.info("[GET_FAIR_IMAGE] Redirecting to presigned URL key={}", key);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, presignedUrl)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<FairResponseDto>> getAllFairs() {
        List<FairResponseDto> fairs = fairService.getAllFair();

        log.info("[GET_ALL_FAIRS] Fairs fetched successfully total={}", fairs.size());
        return ResponseEntity.ok(fairs);
    }

    @GetMapping("/future")
    public PageResponseDto<FairResponseDto> getFutureFairs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        PageResponseDto<FairResponseDto> fairs = fairService.getFutureFairs(page, size, sortBy);
        log.info("[GET_FUTURE_FAIRS] Future fairs fetched successfully page={} size={} sortBy={} totalElements={}",
                page, size, sortBy, fairs.getTotalElements());
        return fairs;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFair(@PathVariable Long id) {
        validationService.verifyIsAdminUser();
        fairService.deleteFair(id);

        log.info("[DELETE_FAIR] Fair deleted successfully fairId={}", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> insertInterest(@PathVariable Long id) {
        fairService.insertInterest(id);

        log.info("[INSERT_INTEREST_FAIR] Interest updated successfully fairId={}", id);
        return ResponseEntity.noContent().build();
    }

    private FairResponseDto toResponse(FairEntity fair) {
        return new FairResponseDto(fair);
    }
}