package com.avocado.ecomus.payload.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record AddCategoryRequest(

        @NotEmpty(message = "Name must not be empty")
        @NotBlank(message = "Name must not be blank")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only space and letter")
        String name
) {
}
