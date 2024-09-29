package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.UserDto;
import com.avocado.ecomus.payload.req.AuthReq;
import com.avocado.ecomus.payload.req.RegisterRequest;


public interface AuthService {
    UserDto login(AuthReq req);
    void register(RegisterRequest request);
}
