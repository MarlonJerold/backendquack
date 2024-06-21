package com.quackfinances.quackfinances.services.strategy;

import com.quackfinances.quackfinances.model.Transaction;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.services.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TransactionStrategy {
    ResponseEntity<?> execute(Transaction transaction, Authentication authentication, AccountRepository repository, TransactionRepository transactionRepository, CategoryService categoryService) throws Exception;
}
