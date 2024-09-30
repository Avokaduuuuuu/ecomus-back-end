package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.exception.ConfirmationTokenExpirationException;
import com.avocado.ecomus.exception.ConfirmationTokenNotFoundException;
import com.avocado.ecomus.exception.TokenConfirmedException;
import com.avocado.ecomus.global.Times;
import com.avocado.ecomus.entity.ConfirmationTokenEntity;
import com.avocado.ecomus.entity.UserEntity;
import com.avocado.ecomus.repository.ConfirmationTokenRepository;
import com.avocado.ecomus.repository.UserRepository;
import com.avocado.ecomus.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenServiceImp implements ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String generateToken(UserEntity user) {
        String token = UUID.randomUUID().toString();

        ConfirmationTokenEntity confirmationTokenEntity = new ConfirmationTokenEntity();
        confirmationTokenEntity.setToken(token);
        confirmationTokenEntity.setUser(user);
        confirmationTokenEntity.setIssuedAt(LocalDateTime.now());
        confirmationTokenEntity.setExpiredAt(LocalDateTime.now().plusMinutes(Times.CONFIRMATION_TOKEN_EXPIRATION));

        confirmationTokenRepository.save(confirmationTokenEntity);
        return token;
    }

    @Override
    public void confirmToken(String token) {
        ConfirmationTokenEntity confirmationTokenEntity = confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException("Token not found"));

        if (confirmationTokenEntity.getConfirmedAt() != null) throw new TokenConfirmedException("Token has been confirmed");

        if (confirmationTokenEntity.getExpiredAt().isBefore(LocalDateTime.now())) throw new ConfirmationTokenExpirationException("Token expired");

        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        UserEntity user = confirmationTokenEntity.getUser();
        user.setActive(true);
        userRepository.save(user);

    }


}
