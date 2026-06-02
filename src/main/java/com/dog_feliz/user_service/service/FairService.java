package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.repository.AddressRepository;
import com.dog_feliz.user_service.repository.FairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    public Page<FairResponseDto> getFutureFairs(int page, int size, String sortedBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        return fairRepository.findByFairDateGreaterThan(LocalDate.now(), pageable)
                .map(FairResponseDto::new);
    }

    public void deleteFair(Long id) {
        FairEntity fair = fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com o id: " + id));
        fair.getImageKeys().forEach(storageService::delete);
        fairRepository.deleteById(id);
    }

    public void insertInterest(Long id) {
        FairEntity fair = fairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira não encontrada"));
        fair.setInterest(fair.getInterest() + 1);
        fairRepository.save(fair);
    }
}