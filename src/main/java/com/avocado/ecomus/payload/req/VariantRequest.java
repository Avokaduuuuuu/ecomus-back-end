package com.avocado.ecomus.payload.req;

public record VariantRequest(
        int sku,
        int quantity,
        double total
) {
}
