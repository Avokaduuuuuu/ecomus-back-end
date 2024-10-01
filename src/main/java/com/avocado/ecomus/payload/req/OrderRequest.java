package com.avocado.ecomus.payload.req;

import java.util.List;

public record OrderRequest(
    int idUser,
    String note,
    double total,
    int idPaymentMethod,
    List<VariantRequest> variantRequests
) {
}
