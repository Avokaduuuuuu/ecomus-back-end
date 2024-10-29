package com.avocado.ecomus.payload.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AddPaymentInformationRequest(

        @NotEmpty(message = "The holder name must not be empty")
        @Size(min = 1, max = 255, message = "Size between 1 and 255")
        String holderName,

        @NotEmpty(message = "The bank's name must not be empty")
        @Size(min = 1, max = 255, message = "Size between 1 and 255")
        String bank,

        @NotNull(message = "Expired date must not be null")
        LocalDate expired,

        @NotEmpty(message = "CCV must not be empty")
        @Size(min = 1, max = 255, message = "Size between 1 and 255")
        String ccv,

        @NotEmpty(message = "Card number must not be empty")
        @Size(min = 1, max = 255, message = "Size between 1 and 255")
        String cardNumber

) {
}
