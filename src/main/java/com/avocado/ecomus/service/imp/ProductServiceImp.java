package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.entity.ProductEntity;
import com.avocado.ecomus.exception.BrandNotFoundException;
import com.avocado.ecomus.exception.CategoryNotFoundException;
import com.avocado.ecomus.payload.req.AddProductRequest;
import com.avocado.ecomus.repository.BrandRepository;
import com.avocado.ecomus.repository.CategoryRepository;
import com.avocado.ecomus.repository.ProductRepository;
import com.avocado.ecomus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void addProduct(AddProductRequest request) {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(request.name());
        productEntity.setPrice(request.price());
        productEntity.setDescription(request.information());
        productEntity.setBrand(
                brandRepository
                        .findById(request.idBrand())
                        .orElseThrow(() -> new BrandNotFoundException("Brand with id " + request.idBrand() + " not found"))
        );

        request
                .categories()
                .forEach(category -> {
                    productEntity.addCategory(
                            categoryRepository
                                    .findById(category)
                                    .orElseThrow(() -> new CategoryNotFoundException("Category with id " + category + " not found" ))
                    );
                });
        productEntity.setCreateDate(LocalDate.now());
        productRepository.save(productEntity);
    }
}
