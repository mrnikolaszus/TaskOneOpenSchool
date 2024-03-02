package org.openschool.mapper;

import org.openschool.dto.CategoryInfoDTO;
import org.openschool.entity.Category;
import org.springframework.stereotype.Component;


@Component
public class CategoryMapper implements Mapper<Category, CategoryInfoDTO> {
    @Override
    public Category toEntity(CategoryInfoDTO categoryInfoDTO) {
        var category = new Category();
        category.setId(categoryInfoDTO.getId());
        category.setName(categoryInfoDTO.getName());


        return category;
    }

    @Override
    public CategoryInfoDTO toDTO(Category category) {
        return CategoryInfoDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
