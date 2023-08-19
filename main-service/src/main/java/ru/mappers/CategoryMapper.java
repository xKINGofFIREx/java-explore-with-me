package ru.mappers;

import dtos.main.category.CategoryDto;
import ru.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName());
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName());
    }

    public static List<CategoryDto> toCategoryDtos(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories)
            categoryDtos.add(CategoryMapper.toCategoryDto(category));
        return categoryDtos;
    }
}
