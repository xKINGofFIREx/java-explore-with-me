package ru.services.category;

import dtos.main.category.CategoryDto;
import ru.exceptions.NotFoundException;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(long catId) throws NotFoundException;

    CategoryDto createCategory(CategoryDto categoryDto);

    void removeCategoryById(long catId) throws NotFoundException;

    CategoryDto updateCategoryById(long catId, CategoryDto categoryDto) throws NotFoundException;
}
