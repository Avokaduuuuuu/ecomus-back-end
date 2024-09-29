package com.avocado.ecomus.payload.req;


import java.util.List;

public record AddProductRequest(
        String name,
        List<Integer> categories,
        String information,
        double price,
        Integer idBrand
) {
}
