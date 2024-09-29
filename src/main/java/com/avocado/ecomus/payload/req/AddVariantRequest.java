package com.avocado.ecomus.payload.req;

import org.springframework.web.multipart.MultipartFile;

public record AddVariantRequest(
        int idProduct,
        int idColor,
        int idSize,
        MultipartFile image,
        int quantity
) {
}
