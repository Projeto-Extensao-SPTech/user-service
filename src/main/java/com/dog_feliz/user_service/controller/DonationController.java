package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.DonationRequestDto;
import com.dog_feliz.user_service.controller.dto.DonationResponseDto;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.service.DonationService;
import com.dog_feliz.user_service.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Object> createDonation(
            @RequestHeader("Authorization") String token,
            @ModelAttribute @Valid DonationRequestDto donationRequest
    ) {
        try {

            String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            String userEmail = jwtService.extractUsername(cleanToken);


            UserEntity user = userRepository.findByMailAddress(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));


            DonationResponseDto response = donationService.createDonation(donationRequest, user.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erro ao processar doação: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<DonationResponseDto>> getAllDonations(
            @RequestHeader("Authorization") String token
    ) {
        try {
            String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            String userEmail = jwtService.extractUsername(cleanToken);

            UserEntity user = userRepository.findByMailAddress(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

            List<DonationResponseDto> donations = donationService.getDonationsByUserId(user.getId());

            return ResponseEntity.ok(donations);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}