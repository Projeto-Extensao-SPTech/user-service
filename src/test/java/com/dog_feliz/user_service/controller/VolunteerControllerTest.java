package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.VolunteerResponseDto;
import com.dog_feliz.user_service.service.JwtService;
import com.dog_feliz.user_service.service.ValidationService;
import com.dog_feliz.user_service.service.VolunteerService;
import com.dog_feliz.user_service.stub.VolunteerStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VolunteerController.class)
@AutoConfigureMockMvc(addFilters = false)
class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VolunteerService volunteerService;

    @MockitoBean
    private ValidationService validationService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @DisplayName("Dada atualização de voluntário, quando PUT /volunteers/{id}, deve validar propriedade pelo user_id do corpo da requisição")
    void givenUpdateWhenPutThenValidatesUserIdFromRequestBody() throws Exception {
        var request = VolunteerStub.validRequest(10L);
        when(volunteerService.updateVolunteer(eq(99L), eq(request)))
                .thenReturn(new VolunteerResponseDto(99L, "msg", LocalDate.now(), 10L));

        mockMvc.perform(put("/volunteers/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(validationService).verifyIsValidUserId(10L);
    }
}
