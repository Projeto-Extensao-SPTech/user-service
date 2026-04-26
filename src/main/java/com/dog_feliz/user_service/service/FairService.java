package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.repository.AddressRepository;
import com.dog_feliz.user_service.repository.FairRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
public class FairService {

    @Value("${uploads.path}")
    private String uploadDir;

    private final FairRepository fairRepository;

    private final AddressRepository addressRepository;

    public FairService(FairRepository fairRepository, AddressRepository addressRepository) {
        this.fairRepository = fairRepository;
        this.addressRepository = addressRepository;
    }

    public FairEntity createFair(FairRequestDto fairRequestDto) throws IOException {

        FairEntity fair = new FairEntity();

        fair.setFairDate(fairRequestDto.getFairDate());

        fair.setFairHour(fairRequestDto.getFairHour());

        AddressEntity address = addressRepository.save(new AddressEntity(fairRequestDto.getAddress()));

        fair.setAddress(address);

        // Caso a pasta não exista na máquina, cria automaticamente
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Aqui convertemos o tipo MultipartFile e associamos
        for (MultipartFile file : fairRequestDto.getImage()) {

            Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            fair.getImages().add(file.getOriginalFilename());
        }

        return fairRepository.save(fair);
    }

    public FairResponseDto getFair(Long id) {
        FairEntity fair = fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com o id: " + id));

        return new FairResponseDto(fair);
    }

    public List<FairResponseDto> getAllFair() {

        List<FairEntity> fairs = fairRepository.findAll();

        return fairs.stream()
                .map(FairResponseDto::new)
                .toList();

    }

    public Page<FairResponseDto> getFutureFairs(
            int page,
            int size,
            String sortedBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        Page<FairEntity> fairs = fairRepository.findByFairDateGreaterThan(LocalDate.now(), pageable);
        return fairs.map(FairResponseDto::new);
    }

    public void deleteFair(Long id) {
        fairRepository.deleteById(id);
    }

    public void insertInterest(Long id) {

        FairEntity fair = fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada"));

        var valueInterest = fair.getInterest();

        valueInterest += 1;

        fair.setInterest(valueInterest);

        fairRepository.save(fair);
    }
}
