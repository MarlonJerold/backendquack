package com.quackfinances.quackfinances.services.service;

import com.quackfinances.quackfinances.dto.Categoty.CategoryRequestDTO;

import java.util.List;

public interface CategoryService {
    CategoryRequestDTO createCategoty(CategoryRequestDTO categoryRequestDTO);
    List<CategoryRequestDTO>  getCategory();
}
