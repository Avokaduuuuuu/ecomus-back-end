package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> fetchAllCategories();
}
