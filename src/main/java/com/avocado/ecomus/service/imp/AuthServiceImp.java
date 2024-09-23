package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.RoleDto;
import com.avocado.ecomus.dto.UserDto;
import com.avocado.ecomus.entity.UserEntity;
import com.avocado.ecomus.exception.UserNotFoundException;
import com.avocado.ecomus.payload.req.AuthReq;
import com.avocado.ecomus.repository.UserRepository;
import com.avocado.ecomus.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto login(AuthReq req) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(req.email());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        UserEntity user = optionalUser.get();
        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        return UserDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(RoleDto
                        .builder()
                        .id(user.getRole().getId())
                        .name(user.getRole().getName())
                        .build())
                .build();
    }
}
