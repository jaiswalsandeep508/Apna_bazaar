package com.ecommerce.service.impl;

import com.ecommerce.dto.request.CategoryRequestDTO;
import com.ecommerce.dto.response.CategoryResponseDTO;
import com.ecommerce.dto.response.CategoryResponsePageDTO;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        ValidateCategoryNameNotExists(dto.getName());
        Category category = modelMapper.map(dto,Category.class);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        categoryRepository.save(category);
        return modelMapper.map(category,CategoryResponseDTO.class);
    }

    private void ValidateCategoryNameNotExists(String name) {
        if(categoryRepository.existsByName(name)){
            throw new ResourceAlreadyExistsException("Category already exists with name " + name);
        }
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        return null;
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }

    @Override
    public CategoryResponsePageDTO getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
