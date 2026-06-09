package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.DonationRequestDto;
import com.dog.feliz.user.service.controller.dto.DonationResponseDto;
import com.dog.feliz.user.service.controller.dto.NotificationSendRequest;
import com.dog.feliz.user.service.controller.dto.NotificationType;
import com.dog.feliz.user.service.entity.DonationEntity;
import com.dog.feliz.user.service.repository.DonationRepository;
import com.dog.feliz.user.service.repository.UserRepository;
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

    private final DonationRepository donationRepository;

    private final UserRepository userRepository;

    private final NotificationService notificationService;

    public DonationService(
            DonationRepository donationRepository,
            UserRepository userRepository,
            NotificationService notificationService
    ) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public DonationResponseDto createDonation(DonationRequestDto requestDto, Long userId) {
        String imagePath = null;

        if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {
            imagePath = saveImage(requestDto.getImage());
        }

        DonationEntity newDonation = new DonationEntity(requestDto, userId, imagePath);
        DonationEntity savedDonation = donationRepository.save(newDonation);

        userRepository.findById(userId).ifPresent(user ->
                notificationService.send(
                        new NotificationSendRequest(
                                NotificationType.DONATION,
                                null,
                                null,
                                savedDonation.getId()
                        )
                )
        );

        return new DonationResponseDto(savedDonation);
    }

    private String saveImage(MultipartFile file) {
        try {

            String uploadDir = "uploads/donations/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage());
        }
    }

    public List<DonationResponseDto> getDonationsByUserId(Long userId) {
        List<DonationEntity> donations = donationRepository.findByUserId(userId.intValue());
        return donations.stream().map(DonationResponseDto::new).toList();
    }

    public DonationEntity getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found by id %d".formatted(id)));
    }
}