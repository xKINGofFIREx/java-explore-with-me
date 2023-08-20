package ru.mappers;

import dtos.main.category.CategoryDto;
import dtos.main.category.NewCategoryDto;
import ru.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName());
    }

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return new Category(
                0,
                newCategoryDto.getName());
    }

    public static List<CategoryDto> toCategoryDtos(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories)
            categoryDtos.add(CategoryMapper.toCategoryDto(category));
        return categoryDtos;
    }
}
