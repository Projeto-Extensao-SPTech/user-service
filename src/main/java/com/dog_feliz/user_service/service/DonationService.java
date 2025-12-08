package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.DonationRequestDto;
import com.dog_feliz.user_service.controller.dto.DonationResponseDto;
import com.dog_feliz.user_service.entity.DonationEntity;
import com.dog_feliz.user_service.repository.DonationRepository;
import com.dog_feliz.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private EvolutionNotificationService evolutionNotificationService; // Serviço de WhatsApp

    @Autowired
    private UserRepository userRepository; // Para buscar o nome do doador


    private final String UPLOAD_DIR = "uploads/";

    public DonationResponseDto createDonation(DonationRequestDto requestDto, Long userId) {
        String imagePath = null;


        if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {
            imagePath = saveImage(requestDto.getImage());
        }


        DonationEntity newDonation = new DonationEntity(requestDto, userId, imagePath);
        DonationEntity savedDonation = donationRepository.save(newDonation);


        userRepository.findById(userId).ifPresent(user -> {
            evolutionNotificationService.sendDonationNotification(savedDonation, user.getName());
        });

        return new DonationResponseDto(savedDonation);
    }

    // Método auxiliar para salvar o arquivo no disco
    private String saveImage(MultipartFile file) {
        try {

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }


            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);


            Files.copy(file.getInputStream(), filePath);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage());
        }
    }

    // Método para listar doações de um usuário específico
    public List<DonationResponseDto> getDonationsByUserId(Long userId) {
        List<DonationEntity> donations = donationRepository.findByUserId(userId.intValue());
        return donations.stream().map(DonationResponseDto::new).toList();
    }
}