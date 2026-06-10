package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.FairRequestDto;
import com.dog.feliz.user.service.controller.dto.FairResponseDto;
import com.dog.feliz.user.service.controller.dto.PageResponseDto;
import com.dog.feliz.user.service.entity.AddressEntity;
import com.dog.feliz.user.service.entity.FairEntity;
import com.dog.feliz.user.service.repository.AddressRepository;
import com.dog.feliz.user.service.repository.UserFairInterestRepository;
import com.dog.feliz.user.service.entity.UserFairInterestEntity;
import com.dog.feliz.user.service.repository.FairRepository;
import com.dog.feliz.user.service.service.storage.StorageService;
import com.dog.feliz.user.service.shared.utils.UserTokenValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final UserTokenValidationUtils userTokenValidationUtils;

    @CacheEvict(value = "fairs", allEntries = true)
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

    @Cacheable(cacheNames = "fairs", key = "'page:' + #page + ':size:' + #size")
    public PageResponseDto<FairResponseDto> getFutureFairs(int page, int size, String sortedBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        return new PageResponseDto<>(
                fairRepository.findByFairDateGreaterThan(LocalDate.now(), pageable)
                        .map(fair -> toResponse(fair, userTokenValidationUtils.getUserId()))
        );
    }

    @CacheEvict(value = "fairs", allEntries = true)
    public void deleteFair(Long id) {
        FairEntity fair = fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com o id: " + id));
        fair.getImageKeys().forEach(storageService::delete);
        fairRepository.deleteById(id);
    }

    @CacheEvict(value = "fairs", allEntries = true)
    public FairEntity insertInterest(Long fairId, Long userId) {
        FairEntity fair = fairRepository.findById(fairId)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com o id: " + fairId));
        boolean jaRegistrado = userFairInterestRepository
                .existsByUserIdAndFairId(userId, fairId);
        if (jaRegistrado) {
            throw new com.dog.feliz.user.service.shared.exception.FairInterestConflictException(
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