package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.shared.exception.ForbiddenUserException;
import com.dog_feliz.user_service.shared.utils.UserTokenValidationUtils;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private final UserTokenValidationUtils userTokenValidationUtils;

    public ValidationService(UserTokenValidationUtils userTokenValidationUtils) {
        this.userTokenValidationUtils = userTokenValidationUtils;
    }

    public void verifyIsAdminUser() {
        if(!userTokenValidationUtils.isAdminUser())
            throw new ForbiddenUserException("Invalid user for this operation");
    }

    public void verifyIsValidUserId(Long userId) {
        if(!userTokenValidationUtils.isValidUserId(userId))
            throw new ForbiddenUserException("Invalid user for this operation");
    }
}
