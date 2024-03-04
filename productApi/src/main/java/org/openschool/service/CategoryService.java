package org.openschool.service;

import org.openschool.dto.CategoryInfoDTO;

import java.util.List;

public interface CategoryService {
    CategoryInfoDTO createCategory(CategoryInfoDTO categoryInfoDTO);
    CategoryInfoDTO getCategoryById(Long id);
    CategoryInfoDTO getCategoryByName(String name);

    List<CategoryInfoDTO> getAllCategories();
    CategoryInfoDTO updateCategory(Long id, CategoryInfoDTO categoryInfoDTO);
    void deleteCategory(Long id);
}
