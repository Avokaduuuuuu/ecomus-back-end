package com.avocado.ecomus.mapper;

import com.avocado.ecomus.dto.ColorDto;
import com.avocado.ecomus.dto.ProductDto;
import com.avocado.ecomus.dto.SizeDto;
import com.avocado.ecomus.entity.ProductEntity;
import com.avocado.ecomus.global.BaseUrl;

import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductDto mapProductDto(ProductEntity product) {
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
    }
}
