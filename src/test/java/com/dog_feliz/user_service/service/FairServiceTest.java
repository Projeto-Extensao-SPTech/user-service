package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.repository.AddressRepository;
import com.dog_feliz.user_service.repository.FairRepository;
import com.dog_feliz.user_service.stub.FairStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FairServiceTest {

    @Mock
    private FairRepository fairRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private FairService fairService;

    @InjectMocks
    private FairStub fairStub;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(fairService, "uploadDir", System.getProperty("java.io.tmpdir"));
    }

    @Test
    @DisplayName("Dado uma chamada para criar uma feira, deve criar e retornar com sucesso")
    void createFair() throws IOException {
        AddressEntity savedAddress = new AddressEntity();
        when(addressRepository.save(any())).thenReturn(savedAddress);

        FairEntity savedFair = new FairEntity();
        savedFair.setId(1L);
        when(fairRepository.save(any())).thenReturn(savedFair);

        FairRequestDto request = fairStub.createNewFair();

        FairEntity fair = fairService.createFair(request);

        assertNotNull(fair);
        assertEquals(1L, fair.getId());
        verify(addressRepository, times(1)).save(any());
        verify(fairRepository, times(1)).save(any());
    }


    @Test
    @DisplayName("Dado uma chamada para criar feira, quando ocorrer erro no upload da imagem deve lançar exceção")
    void createFairException() throws IOException {

        MockMultipartFile failingFile = mock(MockMultipartFile.class);
        when(failingFile.getOriginalFilename()).thenReturn("image.png");
        when(failingFile.getInputStream()).thenThrow(new IOException("Erro ao ler arquivo"));

        FairRequestDto request = fairStub.createNewFair();
        request.setImage(List.of(failingFile));

        when(addressRepository.save(any())).thenReturn(new AddressEntity());

        assertThrows(IOException.class, () -> fairService.createFair(request));
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
    void getFairException() {
        when(fairRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> fairService.getFair(999L));

        assertTrue(exception.getMessage().contains("Feira não encontrada com o id: 999"));
    }


    @Test
    @DisplayName("Dado uma chamada para buscar todas as feiras, deve retornar todas as feiras")
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

        Pageable page = PageRequest.of(0, 10, Sort.by("id"));
        when(fairRepository.findByFairDateGreaterThan(LocalDate.now(), page)).thenReturn(
                new PageImpl<>(List.of(futureFair), page, 1)
        );

        Page<FairResponseDto> fairs = fairService.getFutureFairs(0, 10, "id");

        assertEquals(1, fairs.getContent().size());
    }

    @Test
    @DisplayName("Dado uma chamada para buscar feiras futuras, deve retornar apenas as futuras ordenadas pelo id")
    void getFutureFairsOrdered() {
        AddressEntity address = fairStub.createAddressEntity(1L);

        FairEntity futureFair1 = fairStub.createFairEntity(1L, address);
        futureFair1.setFairDate(LocalDate.now().plusDays(2));

        FairEntity futureFair2 = fairStub.createFairEntity(2L, address);
        futureFair1.setFairDate(LocalDate.now().plusDays(2));

        Pageable page = PageRequest.of(0, 10, Sort.by("id"));
        when(fairRepository.findByFairDateGreaterThan(LocalDate.now(), page)).thenReturn(
                new PageImpl<>(List.of(futureFair1, futureFair2), page, 2)
        );

        Page<FairResponseDto> fairs = fairService.getFutureFairs(0, 10, "id");

        assertEquals(2, fairs.getContent().size());
        assertEquals(futureFair1.getId(), fairs.getContent().getFirst().getId());
        assertEquals(futureFair2.getId(), fairs.getContent().getLast().getId());
    }

    @Test
    @DisplayName("Dado uma chamada para buscar feiras futuras, quando não houver deve retornar lista vazia")
    void getFutureFairsEmpty() {
        Pageable page = PageRequest.of(0, 10, Sort.by("id"));
        when(fairRepository.findByFairDateGreaterThan(LocalDate.now(), page)).thenReturn(Page.empty());

        Page<FairResponseDto> fairs = fairService.getFutureFairs(0, 10, "id");

        assertTrue(fairs.getContent().isEmpty());
    }


    @Test
    @DisplayName("Dado uma chamada para inserir interesse, deve incrementar corretamente")
    void testInsertInterest() {
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
    void testInsertInterestException() {
        when(fairRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> fairService.insertInterest(999L));

        assertEquals("Feira não encontrada", exception.getMessage());
    }


    @Test
    @DisplayName("Dado uma chamada para deletar feira, deve chamar o método do repository corretamente")
    void deleteFair() {
        doNothing().when(fairRepository).deleteById(10L);

        fairService.deleteFair(10L);

        verify(fairRepository, times(1)).deleteById(10L);
    }

    @Test
    @DisplayName("Deve criar a feira com múltiplas imagens corretamente")
    void createFairContentMultipleImageFiles() throws IOException {

        AddressEntity savedAddress = new AddressEntity();
        when(addressRepository.save(any())).thenReturn(savedAddress);

        FairEntity savedFair = new FairEntity();
        savedFair.setId(1L);
        when(fairRepository.save(any())).thenReturn(savedFair);

        MockMultipartFile file1 = new MockMultipartFile(
                "image1",
                "foto1.png",
                "image/png",
                "conteudo1".getBytes()
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "image2",
                "foto2.png",
                "image/png",
                "conteudo2".getBytes()
        );

        FairRequestDto request = fairStub.createNewFair();
        request.setImage(List.of(file1, file2));

        FairEntity fair = fairService.createFair(request);

        assertNotNull(fair);
        assertEquals(1L, fair.getId());

        verify(addressRepository, times(1)).save(any());
        verify(fairRepository, times(1)).save(any());

        assertEquals("foto1.png", request.getImage().get(0).getOriginalFilename());
        assertEquals("foto2.png", request.getImage().get(1).getOriginalFilename());
    }

}
