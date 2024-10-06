package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.global.BaseUrl;
import com.avocado.ecomus.dto.ColorDto;
import com.avocado.ecomus.dto.ProductDto;
import com.avocado.ecomus.dto.SizeDto;
import com.avocado.ecomus.entity.ProductEntity;
import com.avocado.ecomus.exception.BrandNotFoundException;
import com.avocado.ecomus.exception.CategoryNotFoundException;
import com.avocado.ecomus.exception.ProductNotFoundException;
import com.avocado.ecomus.mapper.ProductMapper;
import com.avocado.ecomus.payload.req.AddProductRequest;
import com.avocado.ecomus.repository.BrandRepository;
import com.avocado.ecomus.repository.CategoryRepository;
import com.avocado.ecomus.repository.ProductRepository;
import com.avocado.ecomus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        productEntity.setAvailable(true);
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductDto> getAllProducts(int page) {
        return productRepository
                .findAll(PageRequest.of(page, 10))
                .stream()
                .filter(ProductEntity::isAvailable)
                .map(ProductMapper::mapProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .filter(ProductEntity::isAvailable)
                .map(ProductMapper::mapProductDto)
                .toList();
    }

    @Override
    public ProductDto getProductById(int id) {
        ProductDto productDto = null;
        for (ProductDto products : getAllProducts()) {
            if (products.getId() == id) {
                productDto = products;
            }
        }
        if (productDto == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return productDto;
    }

    @Override
    public void changeProductStatus(int id) {
        ProductEntity productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        productEntity.setAvailable(!productEntity.isAvailable());

        productRepository.save(productEntity);
    }
}
