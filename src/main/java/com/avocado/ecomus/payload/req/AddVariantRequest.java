package com.avocado.ecomus.payload.req;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AddVariantRequest(
        int idProduct,
        int idColor,
        List<Integer> idSize,
        MultipartFile image,
        int quantity
) {
}
