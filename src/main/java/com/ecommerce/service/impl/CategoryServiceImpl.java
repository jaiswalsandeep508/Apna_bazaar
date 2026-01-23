package com.ecommerce.service.impl;

import com.ecommerce.dto.request.CategoryRequestDTO;
import com.ecommerce.dto.response.CategoryResponseDTO;
import com.ecommerce.dto.response.CategoryResponsePageDTO;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotExistsException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category findCategoryById = getCategoryIfExists(id);
        return modelMapper.map(findCategoryById, CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Category findCategoryById = getCategoryIfExists(id);
        ValidateCategoryNameNotExists(dto.getName());
        findCategoryById.setName(dto.getName());
        findCategoryById.setDescription(dto.getDescription());
        findCategoryById.setUpdatedAt(LocalDateTime.now());
        categoryRepository.save(findCategoryById);

        return modelMapper.map(findCategoryById, CategoryResponseDTO.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category findCategoryById = getCategoryIfExists(id);
        categoryRepository.delete(findCategoryById);
    }

    @Override
    public CategoryResponsePageDTO getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort =  sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        List<CategoryResponseDTO> content = categoriesPage.getContent()
                .stream().map(category ->
                        modelMapper.map(category,CategoryResponseDTO.class)).toList();
        CategoryResponsePageDTO categoryResponsePageDTO = new CategoryResponsePageDTO();
        categoryResponsePageDTO.setResponse(content);
        categoryResponsePageDTO.setTotalPages(categoriesPage.getTotalPages());
        categoryResponsePageDTO.setTotalElements(categoriesPage.getTotalElements());
        categoryResponsePageDTO.setPageNumber(categoriesPage.getNumber());
        categoryResponsePageDTO.setPageSize(categoriesPage.getSize());
        categoryResponsePageDTO.setLast(categoriesPage.isLast());

        return categoryResponsePageDTO;
    }


//    --------------Helper class---------------------
    private void ValidateCategoryNameNotExists(String name) {
        if(categoryRepository.existsByName(name)){
            throw new ResourceAlreadyExistsException("Category already exists with name " + name);
        }
    }

    private Category getCategoryIfExists(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotExistsException("Category not exist with category id " + id));
    }
}
