package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.FairRequestDto;
import com.dog.feliz.user.service.controller.dto.FairResponseDto;
import com.dog.feliz.user.service.controller.dto.PageResponseDto;
import com.dog.feliz.user.service.entity.AddressEntity;
import com.dog.feliz.user.service.entity.FairEntity;
import com.dog.feliz.user.service.repository.AddressRepository;
import com.dog.feliz.user.service.repository.FairRepository;
import com.dog.feliz.user.service.service.storage.StorageService;
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

    @CacheEvict(value = "fairs", allEntries = true)
    public FairEntity createFair(FairRequestDto dto) {
        AddressEntity address = addressRepository.save(new AddressEntity(dto.getAddress()));
        List<String> imageKeys = storageService.uploadAll(dto.getImages(), "fair");
        FairEntity fair = new FairEntity();
        fair.setFairDate(dto.getFairDate());
        fair.setFairHour(dto.getFairHour());
        fair.setAddress(address);
        fair.setInterest(0);
        fair.setImageKeys(imageKeys);
        return fairRepository.save(fair);
    }

    public FairEntity getFair(Long id) {
        return fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com o id: " + id));
    }

    public List<FairResponseDto> getAllFair() {
        return fairRepository.findAll().stream()
                .map(FairResponseDto::new)
                .toList();
    }

    @Cacheable(cacheNames = "fairs", key = "'page:' + #page + ':size:' + #size")
    public PageResponseDto<FairResponseDto> getFutureFairs(int page, int size, String sortedBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        return new PageResponseDto<>(
                fairRepository.findByFairDateGreaterThan(LocalDate.now(), pageable)
                        .map(FairResponseDto::new)
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
    public void insertInterest(Long id) {
        FairEntity fair = fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada"));
        fair.setInterest(fair.getInterest() + 1);
        fairRepository.save(fair);
    }
}