package com.quackfinances.quackfinances.services;

import com.quackfinances.quackfinances.model.CategoryModel;
import com.quackfinances.quackfinances.model.TransactionType;
import com.quackfinances.quackfinances.model.UserModel;
import com.quackfinances.quackfinances.repository.CategoryRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.view.controller.dto.CategoryRequestDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public CategoryRequestDTO createCategoty(CategoryRequestDTO categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();

        TransactionType transactionType = TransactionType.valueOf (String.valueOf(categoryRequest.type()));
        if (categoryRequest != null) {
            Optional<UserModel> userS = userRepository.findByEmail(user);

            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setCategoriaName(categoryRequest.categoryName());
            categoryModel.setTransactionType(transactionType);
            categoryModel.setUser(userS.orElse(null));
            CategoryModel categorySave = categoryRepository.save(categoryModel);

            CategoryRequestDTO response = new CategoryRequestDTO(
                    categorySave.categoriaName(),
                    categorySave.transactionType().toString()
            );

            return response;
        }
        return null;
    }

    public List<CategoryRequestDTO> getCategory() {

        List<CategoryModel> categoryModelList = categoryRepository.findAll();

        List<CategoryRequestDTO> categoryRequestDTOS = new ArrayList<>();

        for (CategoryModel categoryModel : categoryModelList) {
            CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO(
                    categoryModel.categoriaName(),
                    categoryModel.transactionType().toString()
            );
            categoryRequestDTOS.add(categoryRequestDTO);
        }

        return categoryRequestDTOS;
    }

}
