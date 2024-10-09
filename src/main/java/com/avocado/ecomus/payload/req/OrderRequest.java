package com.avocado.ecomus.payload.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest(
        @NotEmpty(message = "Id must not be empty")
        @Min(value = 1, message = "Id must be greater or equal to 1")
        int idUser,

        @NotEmpty(message = "Note must not be empty")
        @NotBlank(message = "Note must not be blank")
        @Size(min = 1, max = 2000, message = "Note must be between 1 and 2000")
        String note,

        @NotEmpty(message = "Total must not be empty")
        @Min(value = 1, message = "Total must be greater or equal to 1")
        double total,

        @NotEmpty(message = "Id must not be empty")
        @Min(value = 1, message = "Id must be greater or equal to 1")
        int idPaymentMethod,
        List<VariantRequest> variantRequests
) {
}
