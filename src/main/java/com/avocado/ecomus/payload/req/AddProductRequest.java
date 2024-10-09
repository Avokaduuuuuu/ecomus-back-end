package com.avocado.ecomus.payload.req;


import jakarta.validation.constraints.*;

import java.util.List;

public record AddProductRequest(
        @NotEmpty(message = "Name must not be empty")
        @NotBlank(message = "Name must not be blank")
        String name,

        List<Integer> categories,

        @NotEmpty(message = "Information must not be empty")
        @NotBlank(message = "Information must not be blank")
        String information,

        @Min(value = 1, message = "Value must be greater than or equal to 1")
        @Digits(integer = 10, fraction = 2, message = "The input must be Double")
        double price,

        @NotNull(message = "Brand's id must not be null")
        @Min(value = 1, message = "Id must be greater than or equal to 1")
        Integer idBrand
) {
}
