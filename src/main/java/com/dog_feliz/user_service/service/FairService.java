package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.entity.FairImageEntity;
import com.dog_feliz.user_service.repository.FairRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FairService {

    @Value("${uploads.path}")
    private String uploadDir;

    FairRepository fairRepository;

    public FairService(FairRepository fairRepository) {
        this.fairRepository = fairRepository;
    }

    public FairEntity createFair(FairRequestDto fairRequestDto) throws IOException {

        FairEntity fair = new FairEntity();

        fair.setFairDate(fairRequestDto.getFairDate());
        fair.setFairHour(fairRequestDto.getFairHour());
        fair.setAddress(fairRequestDto.getAddress());

        // Caso a pasta não exista na máquina, cria automaticamente
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Aqui convertemos o tipo MultipartFile para o tipo FairImageEntity e associamos
        for (MultipartFile file : fairRequestDto.getImage()) {

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path filePath = uploadPath.resolve(filename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            FairImageEntity imageEntity = new FairImageEntity();

            imageEntity.setImagePath(filePath.toString());

            imageEntity.setFair(fair);

            fair.getImage().add(imageEntity);
        }

        return fairRepository.save(fair);
    }

    public FairResponseDto getFair(Long id) {

        FairEntity fair = fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com o id: " + id));

        return new FairResponseDto(fair);

    }
}
