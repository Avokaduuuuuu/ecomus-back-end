package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.UserDto;
import com.avocado.ecomus.payload.req.AuthReq;


public interface AuthService {
    UserDto login(AuthReq req);
}
