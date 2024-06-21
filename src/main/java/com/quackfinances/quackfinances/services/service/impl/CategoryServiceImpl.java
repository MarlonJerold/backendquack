package com.quackfinances.quackfinances.services.service.impl;

import com.quackfinances.quackfinances.dto.Categoty.CategoryRequestDTO;
import com.quackfinances.quackfinances.enums.TransactionEnum;
import com.quackfinances.quackfinances.model.Category;
import com.quackfinances.quackfinances.model.User;
import com.quackfinances.quackfinances.repository.CategoryRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.services.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CategoryRequestDTO createCategoty(CategoryRequestDTO categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();

        TransactionEnum transactionEnum = TransactionEnum.valueOf (String.valueOf(categoryRequest.type()));
        if (categoryRequest != null) {
            Optional<User> userS = userRepository.findByEmail(user);

            Category category = new Category();
            category.setCategoriaName(categoryRequest.categoryName());
            category.setTransactionEnum(transactionEnum);
            category.setUser(userS.orElse(null));
            Category categorySave = categoryRepository.save(category);

            CategoryRequestDTO response = new CategoryRequestDTO(
                    categorySave.categoriaName(),
                    categorySave.transactionType().toString()
            );
            return response;
        }
        return null;
    }

    @Override
    public List<CategoryRequestDTO> getCategory() {

        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryRequestDTO> categoryRequestDTOS = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO(
                    category.categoriaName(),
                    category.transactionType().toString()
            );
            categoryRequestDTOS.add(categoryRequestDTO);
        }

        return categoryRequestDTOS;
    }
}
