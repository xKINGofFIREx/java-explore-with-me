package ru.controllers;

import dtos.main.category.CategoryDto;
import dtos.main.category.NewCategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.exceptions.NotFoundException;
import ru.services.category.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestParam(required = false, defaultValue = "0") int from,
                                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(categoryService.getCategories(from, size), HttpStatus.OK);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("catId") long catId) throws NotFoundException {
        return new ResponseEntity<>(categoryService.getCategoryById(catId), HttpStatus.OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(newCategoryDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{catId}")
    public ResponseEntity<Object> removeCategoryById(@PathVariable("catId") long catId) throws NotFoundException {
        categoryService.removeCategoryById(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/admin/categories/{catId}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable("catId") long catId,
                                                          @Valid @RequestBody CategoryDto categoryDto) throws NotFoundException {
        return new ResponseEntity<>(categoryService.updateCategoryById(catId, categoryDto), HttpStatus.OK);
    }
}
