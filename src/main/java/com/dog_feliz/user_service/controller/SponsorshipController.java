package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.SponsorshipRequestDto;
import com.dog_feliz.user_service.controller.dto.SponsorshipResponseDto;
import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.service.SponsorshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/sponsorships")
public class SponsorshipController {

    private final SponsorshipService sponsorshipService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public SponsorshipController(SponsorshipService sponsorshipService) {
        this.sponsorshipService = sponsorshipService;
    }

    @PostMapping
    public ResponseEntity<SponsorshipResponseDto> addSponsorship(@RequestBody SponsorshipRequestDto dto) {
        SponsorshipResponseDto response = sponsorshipService.addSponsorship(dto);
        log.info("[CREATE_SPONSORSHIP] Sponsorship created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SponsorshipResponseDto>> getAllSponsorships() {
        List<SponsorshipResponseDto> list = sponsorshipService.getAllSponsorships();
        log.info("[GET_ALL_SPONSORSHIPS] Sponsorships fetched successfully total={}", list.size());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SponsorshipResponseDto> getSponsorshipById(@PathVariable Long id) {
        SponsorshipResponseDto response = toResponse(sponsorshipService.getSponsorshipById(id));
        log.info("[GET_SPONSORSHIP_BY_ID] Sponsorship fetched successfully sponsorshipId={}", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-sponsor/{sponsorId}")
    public ResponseEntity<List<SponsorshipResponseDto>> getBySponsorId(@PathVariable Long sponsorId) {
        SponsorshipResponseDto response = sponsorshipService.getSponsorshipsBySponsorId(sponsorId);
        log.info("[GET_SPONSORSHIPS_BY_SPONSOR] Sponsorship fetched successfully sponsorId={}", sponsorId);
        return ResponseEntity.ok(Collections.singletonList(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SponsorshipResponseDto> updateSponsorship(
            @PathVariable Long id,
            @RequestBody SponsorshipRequestDto dto
    ) {
        SponsorshipResponseDto response = sponsorshipService.updateSponsorship(id, dto);
        log.info("[UPDATE_SPONSORSHIP] Sponsorship updated successfully sponsorshipId={}", id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsorship(@PathVariable Long id) {
        sponsorshipService.deleteSponsorship(id);
        log.info("[DELETE_SPONSORSHIP] Sponsorship deleted successfully sponsorshipId={}", id);
        return ResponseEntity.noContent().build();
    }

    private SponsorshipResponseDto toResponse(SponsorshipEntity sponsorship) {
        return new SponsorshipResponseDto(sponsorship);
    }
}