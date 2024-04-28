package com.quackfinances.quackfinances.repository;

import com.quackfinances.quackfinances.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {
}
