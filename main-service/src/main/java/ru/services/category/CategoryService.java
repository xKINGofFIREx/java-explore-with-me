package ru.services.category;

import dtos.main.category.CategoryDto;
import dtos.main.category.NewCategoryDto;
import ru.exceptions.NotFoundException;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(long catId) throws NotFoundException;

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void removeCategoryById(long catId) throws NotFoundException;

    CategoryDto updateCategoryById(long catId, CategoryDto categoryDto) throws NotFoundException;
}
