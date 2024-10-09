package com.avocado.ecomus.payload.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AddVariantRequest(
        @NotNull(message = "Product's id must not be null")
        @Min(value = 1, message = "Id must be greater or equal to 1")
        int idProduct,

        @NotNull(message = "Color's id must not be null")
        @Min(value = 1, message = "Id must be greater or equal to 1")
        int idColor,
        List<Integer> idSize,

        @NotNull(message = "Image must not be null")
        MultipartFile image,

        @NotNull(message = "Quantity must not be null")
        @Min(value = 1, message = "Quantity must be greater or equal to 1")
        int quantity
) {
}
