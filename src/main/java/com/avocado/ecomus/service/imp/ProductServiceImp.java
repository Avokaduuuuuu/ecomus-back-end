package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.config.BaseUrl;
import com.avocado.ecomus.dto.ColorDto;
import com.avocado.ecomus.dto.ProductDto;
import com.avocado.ecomus.dto.SizeDto;
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
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setId(product.getId());
                    productDto.setName(product.getName());
                    productDto.setPrice(product.getPrice());
                    productDto.setDescription(product.getDescription());
                    productDto.setSizes(product
                            .getVariants()
                            .stream()
                            .map(
                                    variant ->
                                            SizeDto
                                                .builder()
                                                .id(variant.getSize().getId())
                                                .name(variant.getSize().getName())
                                                .build())
                            .collect(Collectors.toSet())
                    );
                    productDto.setCategories(
                            product
                                .getProductCategories()
                                .stream()
                                .map(productCategory -> productCategory.getCategory().getName())
                                .toList()
                    );

                    productDto.setColors(
                            product
                                    .getVariants()
                                    .stream()
                                    .map(variant -> {
                                        ColorDto colorDto = new ColorDto();
                                        colorDto.setId(variant.getColor().getId());
                                        colorDto.setName(variant.getColor().getName());
                                        colorDto.setImage(BaseUrl.BASE_URL + "/files/" + variant.getImage());
                                        colorDto.setSizes(
                                                product
                                                        .getVariants()
                                                        .stream()
                                                        .map(variantEntity -> {
                                                            SizeDto sizeDto = null;
                                                            if (colorDto.getId() == variantEntity.getColor().getId()) {
                                                                sizeDto = new SizeDto();
                                                                sizeDto.setId(variantEntity.getSize().getId());
                                                                sizeDto.setName(variantEntity.getSize().getName());
                                                                sizeDto.setQuantity(variantEntity.getQuantity());
                                                            }
                                                            return sizeDto;
                                                        })
                                                        .toList()
                                        );
                                        return colorDto;
                                    }).collect(Collectors.toSet())
                    );
                    return productDto;
                })
                .toList();
    }
}
