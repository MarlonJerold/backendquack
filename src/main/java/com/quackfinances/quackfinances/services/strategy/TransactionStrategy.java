package com.quackfinances.quackfinances.services.strategy;

import com.quackfinances.quackfinances.model.TransactionModel;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TransactionStrategy {
    ResponseEntity<?> execute(TransactionModel transactionModel, Authentication authentication, AccountRepository repository, TransactionRepository transactionRepository, CategoryService categoryService) throws Exception;
}
