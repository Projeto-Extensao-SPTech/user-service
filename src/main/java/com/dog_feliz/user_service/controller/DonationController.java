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

    @PostMapping
    public ResponseEntity<Object> createDonation( // Uso Object para poder retornar Erro ou DTO
                                                  @RequestHeader("Authorization") String token,
                                                  @RequestBody @Valid DonationRequestDto donationRequest
    ) {
        try {
            // 1. Limpa o prefixo "Bearer "
            String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            // 2. Extrai o email (username) usando seu JwtService
            // O método extractUsername faz o parse do token e pega o subject
            String userEmail = jwtService.extractUsername(cleanToken);

            // 3. Busca o usuário no banco pelo email
            UserEntity user = userRepository.findByMailAddress(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

            // 4. Cria a doação vinculada ao ID do usuário encontrado
            DonationResponseDto response = donationService.createDonation(donationRequest, user.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erro de autenticação: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DonationResponseDto>> getAllDonations(
            @RequestHeader("Authorization") String token
    ) {
        try {
            // Limpa o prefixo "Bearer "
            String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            // Extrai o email do usuário
            String userEmail = jwtService.extractUsername(cleanToken);

            // Busca o usuário no banco
            UserEntity user = userRepository.findByMailAddress(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

            // Busca todas as doações vinculadas ao usuário
            List<DonationResponseDto> donations = donationService.getDonationsByUserId(user.getId());

            return ResponseEntity.ok(donations);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }



}