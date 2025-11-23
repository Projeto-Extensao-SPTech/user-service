package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.SponsorResponseDto;
import com.dog_feliz.user_service.controller.dto.SponsorshipRequestDto;
import com.dog_feliz.user_service.controller.dto.SponsorshipResponseDto;
import com.dog_feliz.user_service.service.SponsorshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/sponsorships")
public class SponsorshipController {

    private final SponsorshipService sponsorshipService;

    public SponsorshipController(SponsorshipService sponsorshipService) {
        this.sponsorshipService = sponsorshipService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<SponsorshipResponseDto> addSponsorship(@RequestBody SponsorshipRequestDto dto) {
        System.out.println("DTO:" + dto);
        SponsorshipResponseDto response = sponsorshipService.addSponsorship(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<SponsorshipResponseDto>> getAllSponsorships() {
        return ResponseEntity.ok(sponsorshipService.getAllSponsorships());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SponsorshipResponseDto> getSponsorshipById(@PathVariable Long id) {
        return ResponseEntity.ok(sponsorshipService.getSponsorshipById(id));
    }

    // READ BY SPONSOR ID
    @GetMapping("/by-sponsor/{sponsorId}")
    public ResponseEntity<List<SponsorshipResponseDto>> getBySponsorId(@PathVariable Long sponsorId) {
        return ResponseEntity.ok(sponsorshipService.getSponsorshipsBySponsorId(sponsorId));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SponsorshipResponseDto> updateSponsorship(
            @PathVariable Long id,
            @RequestBody SponsorshipRequestDto dto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(sponsorshipService.updateSponsorship(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsorship(@PathVariable Long id) {
        sponsorshipService.deleteSponsorship(id);
        return ResponseEntity.noContent().build();
    }
}
