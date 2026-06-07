package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.service.FairService;
import com.dog_feliz.user_service.service.S3StorageService;
import com.dog_feliz.user_service.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/fairs")
@RequiredArgsConstructor
public class FairController {
    private final FairService fairService;
    private final ValidationService validationService;
    private final S3StorageService storageService;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<FairEntity> createFair(
            @RequestPart FairRequestDto fairRequest,
            @RequestPart MultipartFile[] image) {

        validationService.verifyIsAdminUser();
        fairRequest.setImages(Arrays.asList(image));
        FairEntity fair = fairService.createFair(fairRequest);

        log.info("[CREATE_FAIR] Fair created successfully fairId={}", fair.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(fair);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FairResponseDto> getFair(@PathVariable Long id) {
        FairEntity fair = fairService.getFair(id);

        log.info("[GET_FAIR] Fair fetched successfully fairId={}", id);
        return ResponseEntity.ok(fairService.toResponse(fair, null));
    }

    @GetMapping("/images")
    public ResponseEntity<Void> getImage(@RequestParam String key) {
        String presignedUrl = storageService.getPresignedUrl(key, Duration.ofMinutes(5));

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
    public Page<FairResponseDto> getFutureFairs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fairDate") String sortBy) {

        Page<FairResponseDto> result = fairService.getFutureFairs(page, size, sortBy);

        log.info("[GET_FUTURE_FAIRS] Future fairs fetched successfully page={} size={} " +
                "sortBy={} totalElements={}", page, size, sortBy, result.getTotalElements());
        return result;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFair(@PathVariable Long id) {
        validationService.verifyIsAdminUser();
        fairService.deleteFair(id);
        log.info("[DELETE_FAIR] Fair deleted successfully fairId={}", id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/interest")
    public ResponseEntity<FairResponseDto> insertInterest(
            @PathVariable Long id,
            Authentication authentication) {

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        Long userId = currentUser.getId();
        FairEntity fair = fairService.insertInterest(id, userId);

        log.info("[INSERT_INTEREST_FAIR] Interest updated successfully fairId={}", id);
        return ResponseEntity.ok(fairService.toResponse(fair, userId));
    }
}