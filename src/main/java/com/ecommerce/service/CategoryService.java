package com.ecommerce.service;

import com.ecommerce.dto.request.CategoryRequestDTO;
import com.ecommerce.dto.response.CategoryResponseDTO;
import com.ecommerce.dto.response.CategoryResponsePageDTO;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO dto);
    CategoryResponseDTO getCategoryById(Long id);
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);
    void deleteCategory(Long id);
    CategoryResponsePageDTO getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);
}
