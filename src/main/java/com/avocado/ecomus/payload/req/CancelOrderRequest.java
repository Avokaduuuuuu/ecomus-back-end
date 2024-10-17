package com.avocado.ecomus.payload.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CancelOrderRequest(
        int id,
        @NotEmpty(message = "Message must not be empty")
        @Size(min = 1, max = 1000, message = "Message must between 1 and 1000 words")
        String message
) {
}
