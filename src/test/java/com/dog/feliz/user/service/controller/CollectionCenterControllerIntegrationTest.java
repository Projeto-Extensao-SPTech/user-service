package com.dog.feliz.user.service.controller;

import com.dog.feliz.user.service.entity.AddressEntity;
import com.dog.feliz.user.service.entity.CollectionCenterEntity;
import com.dog.feliz.user.service.repository.AddressRepository;
import com.dog.feliz.user.service.repository.CollectionCenterRepository;
import com.dog.feliz.user.service.stub.AddressStub;
import com.dog.feliz.user.service.support.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CollectionCenterControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CollectionCenterRepository collectionCenterRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void seedData() {
        collectionCenterRepository.deleteAll();
        addressRepository.deleteAll();

        AddressEntity address = new AddressEntity(AddressStub.validRequest());

        CollectionCenterEntity center = new CollectionCenterEntity();
        ReflectionTestUtils.setField(center, "name", "Centro SP");
        ReflectionTestUtils.setField(center, "address", address);

        collectionCenterRepository.save(center);
    }

    @Test
    @DisplayName("Dados centros de coleta existentes, quando GET /collection-centers sem autenticação, deve retornar 200 e a lista")
    void givenCenters_whenListAllWithoutAuth_thenReturnsOk() throws Exception {
        mockMvc.perform(get("/collection-centers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Centro SP"));
    }
}
