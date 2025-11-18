package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.SponsorRequestDto;
import com.dog_feliz.user_service.controller.dto.SponsorResponseDto;
import com.dog_feliz.user_service.service.SponsorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sponsors")
public class SponsorController {

    private final SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<SponsorResponseDto> addSponsor(@RequestBody SponsorRequestDto dto) {
        SponsorResponseDto response = sponsorService.addSponsor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<SponsorResponseDto>> getSponsors() {
        return ResponseEntity.ok(sponsorService.getSponsors());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SponsorResponseDto> getSponsorById(@PathVariable Long id) {
        SponsorResponseDto sponsor = sponsorService.getSponsorById(id);
        return ResponseEntity.ok(sponsor);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SponsorResponseDto> updateSponsor(
            @PathVariable Long id,
            @RequestBody SponsorRequestDto dto
    ) {
        return ResponseEntity.ok(sponsorService.updateSponsor(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable Long id) {
        sponsorService.deleteSponsor(id);
        return ResponseEntity.noContent().build();
    }
}
