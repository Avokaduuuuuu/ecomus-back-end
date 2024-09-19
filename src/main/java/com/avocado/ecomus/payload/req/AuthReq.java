package com.avocado.ecomus.payload.req;

public record AuthReq(
        String email,
        String password
) {
}
