package com.quackfinances.quackfinances.services.strategy.transaction;

import com.quackfinances.quackfinances.model.Transaction;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;

import org.springframework.http.ResponseEntity;

public interface TransactionStrategy {
    ResponseEntity<?> execute(Transaction transaction, AccountRepository repository, TransactionRepository transactionRepository) throws Exception;
}
