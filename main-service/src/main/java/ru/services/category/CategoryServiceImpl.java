package ru.services.category;

import dtos.main.category.CategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.exceptions.NotFoundException;
import ru.mappers.CategoryMapper;
import ru.models.Category;
import ru.repositories.CategoryRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return CategoryMapper.toCategoryDtos(categoryRepository.findAll(PageRequest.of(from, size)).toList());
    }

    @Override
    public CategoryDto getCategoryById(long catId) throws NotFoundException {
        if (!categoryRepository.existsById(catId))
            throw new NotFoundException("Категория не найдена или недоступна");

        return CategoryMapper.toCategoryDto(categoryRepository.getReferenceById(catId));
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void removeCategoryById(long catId) throws NotFoundException {
        if (!categoryRepository.existsById(catId))
            throw new NotFoundException("Категория не найдена или недоступна");

        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategoryById(long catId, CategoryDto categoryDto) throws NotFoundException {
        if (!categoryRepository.existsById(catId))
            throw new NotFoundException("Категория не найдена или недоступна");

        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        return CategoryMapper.toCategoryDto(category);
    }
}
