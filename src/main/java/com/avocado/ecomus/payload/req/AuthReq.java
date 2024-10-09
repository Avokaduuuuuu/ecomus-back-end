package com.avocado.ecomus.payload.req;

import jakarta.validation.constraints.*;

public record AuthReq(
        @NotEmpty(message = "Email must not be empty")
        @NotBlank(message = "Email must not be blank")
        @Size(min = 1, max = 255, message = "Email must between 1 and 255")
        @Email(message = "Email is invalid")
        String email,

        @NotEmpty(message = "Password must not be empty")
        @NotBlank(message = "Password must not be blank")
        @Size(min = 1, max = 30, message = "Password must between 1 and 30")
        String password
) {
}
