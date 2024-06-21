package com.quackfinances.quackfinances.controller;

import com.quackfinances.quackfinances.dto.Categoty.CategoryRequestDTO;

import com.quackfinances.quackfinances.services.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired(required = false)
    private CategoryService categoryService;

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
