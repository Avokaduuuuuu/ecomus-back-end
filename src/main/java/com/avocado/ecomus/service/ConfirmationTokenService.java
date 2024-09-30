package com.avocado.ecomus.service;

import com.avocado.ecomus.entity.UserEntity;

public interface ConfirmationTokenService {
    String generateToken(UserEntity user);
    void confirmToken(String token);
}
