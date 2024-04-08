package com.example.ntproject.service.auth;

import com.example.ntproject.infrastructure.entity.AuthEntity;
import com.example.ntproject.infrastructure.repository.AuthRepository;
import com.example.ntproject.service.user.error.UserNotFound;

public abstract class OwnershipService {

    protected final AuthRepository authRepository;

    public OwnershipService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean isOwner(String username, Long userId) {
        if (userId == null || username == null) {
            return false;
        }

        AuthEntity auth = authRepository.findByUsername(username).orElseThrow(() -> UserNotFound.createByUsername(username));

        return userId == auth.getUser().getId();
    }
}
