package com.avocado.ecomus.service;

import com.avocado.ecomus.payload.req.AddProductRequest;
import org.springframework.stereotype.Service;


public interface ProductService {
    void addProduct(AddProductRequest request);
}
