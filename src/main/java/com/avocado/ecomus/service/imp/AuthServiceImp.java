package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.RoleDto;
import com.avocado.ecomus.dto.UserDto;
import com.avocado.ecomus.entity.AddressEntity;
import com.avocado.ecomus.entity.UserEntity;
import com.avocado.ecomus.enums.RoleEnum;
import com.avocado.ecomus.exception.RoleNotFoundException;
import com.avocado.ecomus.exception.UserAlreadyExistException;
import com.avocado.ecomus.exception.UserNotFoundException;
import com.avocado.ecomus.payload.req.AuthReq;
import com.avocado.ecomus.payload.req.RegisterRequest;
import com.avocado.ecomus.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto login(AuthReq req) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(req.email());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        UserEntity user = optionalUser.get();
        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new BadCredentialsException("Password is incorrect");
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

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistException("User with email " + request.email() + " already exists");
        }
        UserEntity user = new UserEntity();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());
        user.setRole(
                roleRepository
                        .findByName(RoleEnum.ROLE_USER.name())
                        .orElseThrow(() -> new RoleNotFoundException("Role not found"))
        );

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(request.city().toUpperCase());
        addressEntity.setWard(request.ward());
        addressEntity.setDistrict(request.district());
        addressEntity.setStreet(request.street());
        addressEntity.setZip(request.zipcode());

        user.setAddress(addressEntity);

        userRepository.save(user);
    }
}
