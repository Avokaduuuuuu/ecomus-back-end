package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.ProductDto;
import com.avocado.ecomus.payload.req.AddProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    void addProduct(AddProductRequest request);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(int id);
}
