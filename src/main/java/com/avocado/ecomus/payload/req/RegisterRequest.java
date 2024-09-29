package com.avocado.ecomus.payload.req;

public record RegisterRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        String city,
        String district,
        String ward,
        String street,
        String zipcode
) {
}
