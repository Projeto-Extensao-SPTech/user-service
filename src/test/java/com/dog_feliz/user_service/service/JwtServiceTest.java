package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.stub.UserStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "dGVzdC1qd3Qtc2VjcmV0LWtleS0zMi1ieXRlcy1sb25nIQ==");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1_800_000L);
    }

    @Test
    @DisplayName("Dados detalhes do usuário, quando generateToken é chamado, o token deve conter as claims do usuário")
    void givenUser_whenGenerateToken_thenContainsClaims() {
        UserEntity user = UserStub.entityWithId(7L);
        ReflectionTestUtils.setField(user, "mailAddress", "jwt@test.com");
        ReflectionTestUtils.setField(user, "isAdmin", true);

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        Long extractedId = jwtService.extractClaim(token, claims -> claims.get("id", Long.class));
        Boolean extractedIsAdmin = jwtService.extractClaim(token, claims -> claims.get("is_admin", Boolean.class));
        assertEquals(7L, extractedId);
        assertEquals(Boolean.TRUE, extractedIsAdmin);
        assertEquals("jwt@test.com", jwtService.extractUsername(token));
    }

    @Test
    @DisplayName("Dados token e usuário válidos, quando isTokenValid é chamado, a validação deve ser bem-sucedida")
    void givenValidToken_whenIsTokenValid_thenReturnsTrue() {
        UserEntity user = UserStub.entityWithId(1L);
        ReflectionTestUtils.setField(user, "mailAddress", "valid@test.com");

        String token = jwtService.generateToken(user);

        assertTrue(jwtService.isTokenValid(token, user));
    }
}
