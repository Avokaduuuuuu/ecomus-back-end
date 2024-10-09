package com.avocado.ecomus.payload.req;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotEmpty(message = "Email must not be empty")
        @NotBlank(message = "Email must not be blank")
        @Size(min = 1, max = 255, message = "Email must between 1 and 255")
        @Email(message = "Email is invalid")
        String email,

        @NotEmpty(message = "Password must not be empty")
        @NotBlank(message = "Password must not be blank")
        @Size(min = 1, max = 30, message = "Password must between 1 and 30")
        String password,

        @NotEmpty(message = "Firstname must not be empty")
        @NotBlank(message = "Firstname must not be blank")
        String firstName,

        @NotEmpty(message = "Lastname must not be empty")
        @NotBlank(message = "Lastname must not be blank")
        String lastName,

        @NotEmpty(message = "Phone must not be empty")
        @NotBlank(message = "Phone must not be blank")
        @Size(min = 10, max = 10, message = "Phone must contain 10 digits")
        String phone,

        @NotEmpty(message = "City must not be empty")
        @NotBlank(message = "City must not be blank")
        String city,

        @NotEmpty(message = "District must not be empty")
        @NotBlank(message = "District must not be blank")
        String district,

        @NotEmpty(message = "Ward must not be empty")
        @NotBlank(message = "Ward must not be blank")
        String ward,

        @NotEmpty(message = "Street must not be empty")
        @NotBlank(message = "Street must not be blank")
        String street,

        @NotEmpty(message = "Zipcode must not be empty")
        @NotBlank(message = "Zipcode must not be blank")
        @Pattern(regexp = "^\\d+$", message = "Zipcode must contain only digit")
        String zipcode
) {
}
