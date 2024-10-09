package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.CategoryDto;
import com.avocado.ecomus.entity.CategoryEntity;
import com.avocado.ecomus.exception.CategoryAlreadyExistsException;
import com.avocado.ecomus.exception.InvalidInputException;
import com.avocado.ecomus.payload.req.AddCategoryRequest;
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

    @Override
    public void addCategory(AddCategoryRequest request) {
        categoryRepository
                .findByName(request.name())
                .ifPresent(category -> {
                    throw new CategoryAlreadyExistsException("Category already exists");
                });

        if (request.name().isEmpty()) throw new InvalidInputException("Category must not be empty");
        if (!request.name().chars().allMatch(ch -> Character.isAlphabetic(ch) || Character.isWhitespace(ch))) throw new InvalidInputException("Category must contain letter");

        categoryRepository.save(
                CategoryEntity.builder().name(request.name()).build()
        );
    }
}
