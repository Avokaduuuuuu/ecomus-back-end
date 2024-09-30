package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.UserDto;
import com.avocado.ecomus.payload.req.AuthReq;
import com.avocado.ecomus.payload.req.RegisterRequest;
import jakarta.mail.MessagingException;


public interface AuthService {
    UserDto login(AuthReq req);
    void register(RegisterRequest request) throws MessagingException;
}
