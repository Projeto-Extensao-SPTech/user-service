package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.entity.UserFairInterestEntity;
import com.dog_feliz.user_service.repository.AddressRepository;
import com.dog_feliz.user_service.repository.FairRepository;
import com.dog_feliz.user_service.repository.UserFairInterestRepository;
import com.dog_feliz.user_service.shared.exception.FairInterestConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FairService {
    private final FairRepository fairRepository;
    private final AddressRepository addressRepository;
    private final StorageService storageService;
    private final UserFairInterestRepository userFairInterestRepository;

    public FairEntity createFair(FairRequestDto dto) {
        AddressEntity address = addressRepository.save(new AddressEntity(dto.getAddress()));
        List<String> imageKeys = storageService.uploadAll(dto.getImages(), "fairs");
        FairEntity fair = new FairEntity();
        fair.setFairDate(dto.getFairDate());
        fair.setFairHour(dto.getFairHour());
        fair.setAddress(address);
        fair.setImageKeys(imageKeys);
        return fairRepository.save(fair);
    }

    public FairEntity getFair(Long id) {
        return fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com o id: " + id));
    }

    public List<FairResponseDto> getAllFair() {
        return fairRepository.findAll()
                .stream()
                .map(fair -> toResponse(fair, null))
                .toList();
    }

    public Page<FairResponseDto> getFutureFairs(int page, int size, String sortBy) {
        var pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return fairRepository
                .findByFairDateGreaterThan(LocalDate.now(), pageable)
                .map(fair -> toResponse(fair, null));
    }

    public void deleteFair(Long id) {
        FairEntity fair = fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com o id: " + id));
        fair.getImageKeys().forEach(storageService::delete);
        fairRepository.deleteById(id);
    }

    public FairEntity insertInterest(Long fairId, Long userId) {
        FairEntity fair = fairRepository.findById(fairId)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com o id: " + fairId));
        boolean jaRegistrado = userFairInterestRepository
                .existsByUserIdAndFairId(userId, fairId);
        if (jaRegistrado) {
            throw new FairInterestConflictException(
                    "Usuário já registrou interesse nessa feira."
            );
        }
        userFairInterestRepository.save(new UserFairInterestEntity(userId, fairId));
        return fair;
    }

    public FairResponseDto toResponse(FairEntity fair, Long userId) {
        long totalInterest = userFairInterestRepository.countByFairId(fair.getId());
        boolean userHasInterest = userId != null &&
                userFairInterestRepository.existsByUserIdAndFairId(userId, fair.getId());
        return new FairResponseDto(fair, totalInterest, userHasInterest);
    }
}