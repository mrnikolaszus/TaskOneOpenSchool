package org.openschool.controller;

import org.openschool.dto.CategoryInfoDTO;
import org.openschool.exception.CategoryNotFoundException;
import org.openschool.exception.ValidationApiException;
import org.openschool.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryInfoDTO createCategory(@RequestBody @Validated CategoryInfoDTO categoryInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationApiException("Invalid data: " + bindingResult.getAllErrors());
        return categoryService.createCategory(categoryInfoDTO);
    }

    @GetMapping
    public List<CategoryInfoDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryInfoDTO getCategoryById(@PathVariable Long id) {
        CategoryInfoDTO categoryInfoDTO = categoryService.getCategoryById(id);
        if (categoryInfoDTO == null)
            throw new CategoryNotFoundException("Category with ID " + id + " not found");
        return categoryInfoDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryInfoDTO updateCategory(@PathVariable Long id, @RequestBody @Validated CategoryInfoDTO categoryInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationApiException("Invalid data: " + bindingResult.getAllErrors());

        CategoryInfoDTO existingCategoryInfoDTO = categoryService.getCategoryById(id);
        if (existingCategoryInfoDTO == null)
            throw new CategoryNotFoundException("Category with ID " + id + " not found");

        if (!categoryInfoDTO.getId().equals(id))
            throw new ValidationApiException("ID in the path does not match ID in the body");

        return categoryService.updateCategory(id, categoryInfoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable Long id) {
        CategoryInfoDTO categoryInfoDTO = categoryService.getCategoryById(id);
        if (categoryInfoDTO == null)
            throw new CategoryNotFoundException("Category with ID " + id + " not found");
        categoryService.deleteCategory(id);
    }
}
