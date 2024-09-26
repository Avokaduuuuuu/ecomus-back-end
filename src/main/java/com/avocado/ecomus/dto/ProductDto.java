package com.avocado.ecomus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private int id;
    private String name;
    private double price;
    private String description;
    private Set<SizeDto> sizes;
    private List<String> categories;
    private Set<ColorDto> colors;
}
