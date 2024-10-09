package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.CategoryDto;
import com.avocado.ecomus.payload.req.AddCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> fetchAllCategories();
    void addCategory(AddCategoryRequest request);
}
