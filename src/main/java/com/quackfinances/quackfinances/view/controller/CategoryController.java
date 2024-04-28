package com.quackfinances.quackfinances.view.controller;

import com.quackfinances.quackfinances.services.CategoryService;
import com.quackfinances.quackfinances.view.controller.dto.CategoryRequestDTO;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryRequestDTO createCategory(@RequestBody CategoryRequestDTO categoryRequest) {
        return categoryService.createCategoty(categoryRequest);
    }

    @GetMapping
    public List<CategoryRequestDTO> getListCategory() {
        List<CategoryRequestDTO> categoryList = categoryService.getCategory();
        return categoryList;
    }
}
