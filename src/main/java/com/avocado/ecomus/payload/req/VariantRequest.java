package com.avocado.ecomus.payload.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record VariantRequest(
        @NotEmpty(message = "SKU must not be empty")
        @NotBlank(message = "SKU must not be blank")
        @Min(value = 1, message = "SKU must be greater or equal to 1")
        int sku,
        @NotEmpty(message = "Quantity must not be empty")
        @NotBlank(message = "Quantity must not be blank")
        @Min(value = 1, message = "Quantity must be greater or equal to 1")
        int quantity,

        @NotEmpty(message = "Quantity must not be empty")
        @NotBlank(message = "Quantity must not be blank")
        @Min(value = 1, message = "Total must be greater or equal to 1")
        double total
) {
}
