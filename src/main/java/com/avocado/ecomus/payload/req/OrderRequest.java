package com.avocado.ecomus.payload.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest(

        int idUser,

        @NotEmpty(message = "First name must not be empty")
        @NotBlank(message = "First name must not be blank")
        @Size(min = 1, max = 50, message = "First name must be between 1 and 50")
        String firstName,

        @NotEmpty(message = "Last name must not be empty")
        @NotBlank(message = "Last name must not be blank")
        @Size(min = 1, max = 50, message = "Last name must be between 1 and 50")
        String lastName,


        @NotEmpty(message = "City name must not be empty")
        @NotBlank(message = "City name must not be blank")
        @Size(min = 1, max = 250, message = "City name must be between 1 and 250")
        String city,

        @NotEmpty(message = "District must not be empty")
        @NotBlank(message = "District must not be blank")
        @Size(min = 1, max = 250, message = "District must be between 1 and 250")
        String district,

        @NotEmpty(message = "Ward must not be empty")
        @NotBlank(message = "Ward must not be blank")
        @Size(min = 1, max = 250, message = "Ward must be between 1 and 250")
        String ward,

        @NotEmpty(message = "Street must not be empty")
        @NotBlank(message = "Street must not be blank")
        @Size(min = 1, max = 250, message = "Street must be between 1 and 250")
        String street,

        @NotEmpty(message = "Phone must not be empty")
        @NotBlank(message = "Phone must not be blank")
        @Size(min = 1, max = 12, message = "Phone must be between 1 and 12")
        String phone,

        @NotEmpty(message = "Email must not be empty")
        @NotBlank(message = "Email must not be blank")
        @Size(min = 1, max = 250, message = "City name must be between 1 and 250")
        String email,

        @NotEmpty(message = "Note must not be empty")
        @NotBlank(message = "Note must not be blank")
        @Size(min = 1, max = 2000, message = "Note must be between 1 and 2000")
        String note,

        @NotEmpty(message = "Zipcode must not be empty")
        @NotBlank(message = "Zipcode must not be blank")
        @Size(min = 1, max = 12, message = "Zipcode must be between 1 and 12")
        String zipcode,

        @Min(value = 1, message = "Total must be greater or equal to 1")
        double total,

        @Min(value = 1, message = "Shipping fee must be greater or equal to 1")
        double shippingFee,

        @Min(value = 1, message = "Id must be greater or equal to 1")
        int idPaymentMethod,
        List<VariantRequest> variantRequests
) {
}
