package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.CategoryDto;
import com.avocado.ecomus.repository.CategoryRepository;
import com.avocado.ecomus.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> fetchAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryEntity -> CategoryDto
                        .builder()
                        .id(categoryEntity.getId())
                        .name(categoryEntity.getName())
                        .build()
                )
                .toList();
    }
}
