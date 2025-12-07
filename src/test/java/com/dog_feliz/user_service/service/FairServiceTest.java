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
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
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
    @DisplayName("Dado uma chamada para criar uma feira de adoção, deve criar uma feira de adoção nova e retornar com sucesso")
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
    @DisplayName("Dado uma chamada para buscar uma feira de adoção pelo ID, quando buscar pelo ID correto, deve retornar com sucesso a feira")
    void getFair() {
        AddressEntity address = fairStub.createAddressEntity(1L);
        FairEntity fair = fairStub.createFairEntity(10L, address);

        when(fairRepository.findById(10L)).thenReturn(Optional.of(fair));

        FairResponseDto response = fairService.getFair(10L);

        assertNotNull(response);
        assertEquals(10L, response.getId());
    }

    @Test
    @DisplayName("Dado uma chamada para buscar todas as feiras de adoção criadas, quando buscar pelas feiras, deve retornar com sucesso todas as feiras")
    void getAllFairs() {
        AddressEntity address = fairStub.createAddressEntity(10L);
        FairEntity firstFair = fairStub.createFairEntity(1L, address);
        FairEntity secondFair = fairStub.createFairEntity(1L, address);

        when(fairRepository.findAll()).thenReturn(List.of(firstFair, secondFair));

        List<FairResponseDto> fairList = fairService.getAllFair();

        assertEquals(2, fairList.size());
    }

    @Test
    @DisplayName("Dado uma chamada para inserir/demonstrar interesse na feira de adoção disponível, deve registrar o interesse com sucesso")
    void testInsertInterest() {
        AddressEntity address = fairStub.createAddressEntity(1L);
        FairEntity fair = fairStub.createFairEntity(1L, address);

        fair.setInterest(3);

        when(fairRepository.findById(1L)).thenReturn(Optional.of(fair));

        fairService.insertInterest(1L);

        assertEquals(4, fair.getInterest());
        verify(fairRepository, times(1)).save(fair);
    }

    @Test
    @DisplayName("Dado uma chamada para deletar uma feira pelo ID, quando buscar a feira deve validar que deletou corretamente")
    void deleteFair() {

        doNothing().when(fairRepository).deleteById(10L);

        fairService.deleteFair(10L);

        verify(fairRepository, times(1)).deleteById(10L);
    }



}
