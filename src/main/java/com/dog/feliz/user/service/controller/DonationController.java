package com.dog.feliz.user.service.controller;

import com.dog.feliz.user.service.controller.dto.DonationRequestDto;
import com.dog.feliz.user.service.controller.dto.DonationResponseDto;
import com.dog.feliz.user.service.entity.user.UserEntity;
import com.dog.feliz.user.service.repository.UserRepository;
import com.dog.feliz.user.service.service.DonationService;
import com.dog.feliz.user.service.service.JwtService;
import com.dog.feliz.user.service.shared.crypto.hash.StringHasher;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {

    private final DonationService donationService;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final StringHasher stringHasher;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public DonationController(
            DonationService donationService,
            JwtService jwtService,
            UserRepository userRepository,
            StringHasher stringHasher
    ) {
        this.donationService = donationService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.stringHasher = stringHasher;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Object> createDonation(
            @RequestHeader("Authorization") String token,
            @ModelAttribute @Valid DonationRequestDto donationRequest
    ) {
        try {
            String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            String userEmail = jwtService.extractUsername(cleanToken);

            UserEntity user = userRepository.findByMailAddressHash(stringHasher.hash(userEmail))
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

            DonationResponseDto response = donationService.createDonation(donationRequest, user.getId());

            log.info(
                    "[CREATE_DONATION] Donation created successfully for userId={} request={}",
                    user.getId(),
                    donationRequest
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.error("[CREATE_DONATION] Token expired while creating donation request={}", donationRequest, e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado.");
        } catch (Exception e) {
            log.error(
                    "[CREATE_DONATION] Error while creating donation request={} message={}",
                    donationRequest,
                    e.getMessage(),
                    e
            );
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

            UserEntity user = userRepository.findByMailAddressHash(stringHasher.hash(userEmail))
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

            List<DonationResponseDto> donations = donationService.getDonationsByUserId(user.getId());

            log.info(
                    "[GET_DONATIONS] Donations fetched successfully for userId={} total={}",
                    user.getId(),
                    donations.size()
            );

            return ResponseEntity.ok(donations);

        } catch (Exception e) {
            log.error("[GET_DONATIONS] Error while fetching donations message={}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}