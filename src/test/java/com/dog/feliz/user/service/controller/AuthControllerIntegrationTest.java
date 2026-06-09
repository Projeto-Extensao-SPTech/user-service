package com.dog.feliz.user.service.controller;

import com.dog.feliz.user.service.entity.user.UserType;
import com.dog.feliz.user.service.repository.UserRepository;
import com.dog.feliz.user.service.shared.crypto.hash.StringHasher;
import com.dog.feliz.user.service.support.IntegrationTestBase;
import com.dog.feliz.user.service.support.TestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringHasher stringHasher;

    @Test
    @DisplayName("Dado payload de cadastro inválido, quando POST /auth/register, deve retornar status 400")
    void givenInvalidPayloadWhenRegisterThenReturnsBadRequest() throws Exception {
        Map<String, Object> invalidUser = Map.of(
                "type", "PF",
                "mail_address", "invalid-email"
        );

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Dado cadastro válido com admin solicitado, quando POST /auth/register, deve retornar 201 e usuário persistido sem privilégio de admin")
    void givenValidRegistrationWhenRegisterThenReturnsCreatedWithoutAdminPrivileges() throws Exception {
        String email = "register-test@example.com";
        Map<String, Object> user = validRegistrationPayload(email, true);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mail_address").value(email));

        var persisted = userRepository.findByMailAddressHash(stringHasher.hash(email));
        assertTrue(persisted.isPresent());
        assertFalse(persisted.get().getIsAdmin());
    }

    @Test
    @DisplayName("Dado e-mail duplicado, quando POST /auth/register é chamado duas vezes, a segunda requisição deve retornar 409")
    void givenDuplicateEmail_whenRegisterTwice_thenReturnsConflict() throws Exception {
        Map<String, Object> user = validRegistrationPayload("duplicate@example.com", false);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Dadas credenciais inválidas, quando POST /auth/login, deve retornar status 401")
    void givenInvalidCredentialsWhenLoginThenReturnsUnauthorized() throws Exception {
        Map<String, String> login = Map.of(
                "mail_address", "nonexistent@example.com",
                "password", "wrong-password"
        );

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Dado usuário cadastrado, quando POST /auth/login, deve retornar os tokens de autenticação")
    void givenRegisteredUserWhenLoginThenReturnsTokens() throws Exception {
        String email = "login-flow@example.com";
        registerUser(email);

        Map<String, String> login = Map.of(
                "mail_address", email,
                "password", TestConstants.VALID_PASSWORD
        );

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", not(emptyString())))
                .andExpect(jsonPath("$.refresh_token", not(emptyString())));
    }

    private void registerUser(String email) throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegistrationPayload(email, false))));
    }

    private Map<String, Object> validRegistrationPayload(String email, boolean requestAdmin) {
        return Map.of(
                "type", UserType.PF.name(),
                "name", TestConstants.VALID_NAME,
                "document", TestConstants.VALID_DOCUMENT,
                "mail", TestConstants.VALID_PHONE,
                "mail_address", email,
                "password", TestConstants.VALID_PASSWORD,
                "is_admin", requestAdmin,
                "address", Map.of(
                        "zip_code", TestConstants.VALID_ZIP_CODE,
                        "number", 100,
                        "street", "Avenida Paulista",
                        "complement", "Apto 1",
                        "city", "São Paulo",
                        "state", "SP",
                        "country", "Brasil"
                )
        );
    }
}
