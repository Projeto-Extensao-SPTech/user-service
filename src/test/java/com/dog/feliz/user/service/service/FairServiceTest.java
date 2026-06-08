package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.FairRequestDto;
import com.dog.feliz.user.service.controller.dto.FairResponseDto;
import com.dog.feliz.user.service.controller.dto.PageResponseDto;
import com.dog.feliz.user.service.entity.AddressEntity;
import com.dog.feliz.user.service.entity.FairEntity;
import com.dog.feliz.user.service.repository.AddressRepository;
import com.dog.feliz.user.service.repository.FairRepository;
import com.dog.feliz.user.service.service.storage.S3StorageService;
import com.dog.feliz.user.service.shared.exception.StorageException;
import com.dog.feliz.user.service.stub.FairStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FairServiceTest {

    @Mock
    private FairRepository fairRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private S3StorageService storageService;

    @InjectMocks
    private FairService fairService;

    private FairStub fairStub;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        fairStub = new FairStub();
    }


    @Test
    @DisplayName("Dado uma chamada para criar uma feira, deve criar e retornar com sucesso")
    void createFair() {
        AddressEntity savedAddress = new AddressEntity();
        when(addressRepository.save(any())).thenReturn(savedAddress);

        List<String> uploadedKeys = List.of("fair/uuid_foto.png");
        when(storageService.uploadAll(any(), eq("fair"))).thenReturn(uploadedKeys);

        FairEntity savedFair = new FairEntity();
        savedFair.setId(1L);
        savedFair.setImageKeys(uploadedKeys);
        when(fairRepository.save(any())).thenReturn(savedFair);

        FairRequestDto request = fairStub.createNewFair();
        FairEntity fair = fairService.createFair(request);

        assertNotNull(fair);
        assertEquals(1L, fair.getId());
        assertEquals(uploadedKeys, fair.getImageKeys());

        verify(addressRepository, times(1)).save(any());
        verify(storageService, times(1)).uploadAll(any(), eq("fair"));
        verify(fairRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Dado uma chamada para criar feira com múltiplas imagens, deve fazer upload de todas em paralelo")
    void createFairWithMultipleImages() {
        MockMultipartFile file1 = new MockMultipartFile("image1", "foto1.png", "image/png", "bytes1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("image2", "foto2.png", "image/png", "bytes2".getBytes());

        FairRequestDto request = fairStub.createNewFair();
        request.setImages(List.of(file1, file2));

        when(addressRepository.save(any())).thenReturn(new AddressEntity());

        List<String> uploadedKeys = List.of("fair/uuid-1_foto1.png", "fair/uuid-2_foto2.png");
        when(storageService.uploadAll(any(), eq("fair"))).thenReturn(uploadedKeys);

        FairEntity savedFair = new FairEntity();
        savedFair.setId(1L);
        savedFair.setImageKeys(uploadedKeys);
        when(fairRepository.save(any())).thenReturn(savedFair);

        FairEntity fair = fairService.createFair(request);

        assertNotNull(fair);
        assertEquals(2, fair.getImageKeys().size());
        verify(storageService, times(1)).uploadAll(eq(List.of(file1, file2)), eq("fair"));
    }

    @Test
    @DisplayName("Dado uma chamada para criar feira, quando o upload falhar deve lançar StorageException")
    void createFairUploadFailure() {
        when(addressRepository.save(any())).thenReturn(new AddressEntity());
        when(storageService.uploadAll(any(), eq("fair")))
                .thenThrow(new StorageException("Falha no upload", new RuntimeException()));

        FairRequestDto request = fairStub.createNewFair();

        assertThrows(StorageException.class, () -> fairService.createFair(request));

        // fair não deve ser salva se o upload falhou
        verify(fairRepository, never()).save(any());
    }

    @Test
    @DisplayName("Dado uma chamada para buscar feira por ID, quando existir deve retornar")
    void getFair() {
        AddressEntity address = fairStub.createAddressEntity(1L);
        FairEntity fair = fairStub.createFairEntity(10L, address);

        when(fairRepository.findById(10L)).thenReturn(Optional.of(fair));

        FairEntity fairEntity = fairService.getFair(10L);

        assertNotNull(fairEntity);
        assertEquals(10L, fairEntity.getId());
    }

    @Test
    @DisplayName("Dado uma chamada para buscar feira por ID, quando não existir deve lançar erro")
    void getFairNotFound() {
        when(fairRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> fairService.getFair(999L));

        assertTrue(exception.getMessage().contains("Feira não encontrada com o id: 999"));
    }

    @Test
    @DisplayName("Dado uma chamada para buscar todas as feiras, deve retornar todas")
    void getAllFairs() {
        AddressEntity address = fairStub.createAddressEntity(10L);
        FairEntity f1 = fairStub.createFairEntity(1L, address);
        FairEntity f2 = fairStub.createFairEntity(2L, address);

        when(fairRepository.findAll()).thenReturn(List.of(f1, f2));

        List<FairResponseDto> fairs = fairService.getAllFair();

        assertEquals(2, fairs.size());
    }


    @Test
    @DisplayName("Dado uma chamada para buscar feiras futuras, deve retornar apenas as futuras")
    void getFutureFairs() {
        AddressEntity address = fairStub.createAddressEntity(1L);
        FairEntity futureFair = fairStub.createFairEntity(1L, address);
        futureFair.setFairDate(LocalDate.now().plusDays(2));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        when(fairRepository.findByFairDateGreaterThan(LocalDate.now(), pageable))
                .thenReturn(new PageImpl<>(List.of(futureFair), pageable, 1));

        PageResponseDto<FairResponseDto> fairs = fairService.getFutureFairs(0, 10, "id");

        assertEquals(1, fairs.getData().size());
    }

    @Test
    @DisplayName("Dado uma chamada para buscar feiras futuras, deve retornar ordenadas por ID")
    void getFutureFairsOrdered() {
        AddressEntity address = fairStub.createAddressEntity(1L);
        FairEntity fair1 = fairStub.createFairEntity(1L, address);
        FairEntity fair2 = fairStub.createFairEntity(2L, address);
        fair1.setFairDate(LocalDate.now().plusDays(2));
        fair2.setFairDate(LocalDate.now().plusDays(3));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        when(fairRepository.findByFairDateGreaterThan(LocalDate.now(), pageable))
                .thenReturn(new PageImpl<>(List.of(fair1, fair2), pageable, 2));

        PageResponseDto<FairResponseDto> fairs = fairService.getFutureFairs(0, 10, "id");

        assertEquals(2, fairs.getData().size());
        assertEquals(fair1.getId(), fairs.getData().getFirst().getId());
        assertEquals(fair2.getId(), fairs.getData().getLast().getId());
    }

    @Test
    @DisplayName("Dado uma chamada para buscar feiras futuras, quando não houver deve retornar lista vazia")
    void getFutureFairsEmpty() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        when(fairRepository.findByFairDateGreaterThan(LocalDate.now(), pageable)).thenReturn(Page.empty());

        PageResponseDto<FairResponseDto> fairs = fairService.getFutureFairs(0, 10, "id");

        assertTrue(fairs.getData().isEmpty());
    }


    @Test
    @DisplayName("Dado uma chamada para deletar feira, deve remover imagens do S3 e deletar do banco")
    void deleteFair() {
        AddressEntity address = fairStub.createAddressEntity(1L);
        FairEntity fair = fairStub.createFairEntityWithMultipleImages(10L, address);

        when(fairRepository.findById(10L)).thenReturn(Optional.of(fair));
        doNothing().when(storageService).delete(anyString());
        doNothing().when(fairRepository).deleteById(10L);

        fairService.deleteFair(10L);

        verify(storageService, times(fair.getImageKeys().size())).delete(anyString());
        verify(fairRepository, times(1)).deleteById(10L);
    }

    @Test
    @DisplayName("Dado uma chamada para deletar feira, quando não existir deve lançar erro")
    void deleteFairNotFound() {
        when(fairRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> fairService.deleteFair(999L));

        verify(storageService, never()).delete(anyString());
        verify(fairRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Dado uma chamada para inserir interesse, deve incrementar corretamente")
    void insertInterest() {
        AddressEntity address = fairStub.createAddressEntity(1L);
        FairEntity fair = fairStub.createFairEntity(1L, address);
        fair.setInterest(5);

        when(fairRepository.findById(1L)).thenReturn(Optional.of(fair));

        fairService.insertInterest(1L);

        assertEquals(6, fair.getInterest());
        verify(fairRepository, times(1)).save(fair);
    }

    @Test
    @DisplayName("Dado uma chamada para inserir interesse, quando a feira não existir deve lançar erro")
    void insertInterestNotFound() {
        when(fairRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> fairService.insertInterest(999L));

        assertEquals("Feira não encontrada", exception.getMessage());
        verify(fairRepository, never()).save(any());
    }
}