package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.entity.user.UserEntity;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public TokenService(JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public Pair<String, String> generateToken(UserEntity userEntity, String refreshToken) {
        String newRefreshToken = refreshTokenService.generate(refreshToken, userEntity.getId());
        String jwtToken = jwtService.generateToken(userEntity);
        return Pair.of(jwtToken, newRefreshToken);
    }

    public void revokeToken(String refreshToken) {
        refreshTokenService.revoke(refreshToken);
    }
}
