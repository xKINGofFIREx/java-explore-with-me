package ru.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @GetMapping("/categories")
    public void getCategories() {

    }

    @GetMapping("/categories/{catId}")
    public void getCategoryById(@PathVariable("catId") long catId) {

    }

    @PostMapping("/admin/categories")
    public void createCategory() {

    }

    @DeleteMapping("/admin/categories/{catId}")
    public void removeCategoryById(@PathVariable("catId") long catId) {

    }

    @PatchMapping("/admin/categories/{catId}")
    public void updateCategoryById(@PathVariable("catId") long catId) {

    }
}
