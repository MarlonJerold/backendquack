package com.quackfinances.quackfinances.services.strategy;

import com.quackfinances.quackfinances.exceptions.AccountNotFoundException;
import com.quackfinances.quackfinances.exceptions.InsufficientBalanceException;
import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.model.TransactionModel;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.services.CategoryService;
import com.quackfinances.quackfinances.view.controller.dto.AccountUserLoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class TransferTransactionStrategy implements TransactionStrategy{
    @Override
    public ResponseEntity<?> execute(TransactionModel transactionModel, Authentication authentication, AccountRepository repository, TransactionRepository transactionRepository, CategoryService categoryService) throws Exception {
        Optional<Account> sourceAccount = repository.findById(transactionModel.getSourceAccount().longValue());
        Optional<Account> destinationAccount = repository.findById(transactionModel.getDestinationAccountId().longValue());
        if (destinationAccount.isEmpty()) throw new AccountNotFoundException();

        if (transactionModel.getValue().compareTo(sourceAccount.get().getValue()) > 0) throw new InsufficientBalanceException();

        sourceAccount.get().setValue(sourceAccount.get().getValue().subtract(transactionModel.getValue()));
        destinationAccount.get().setValue(destinationAccount.get().getValue().add(transactionModel.getValue()));

        repository.save(destinationAccount.get());
        transactionRepository.save(transactionModel);
        Account accountSave = repository.save(sourceAccount.get());

        AccountUserLoginDTO accountSaveDTO = new AccountUserLoginDTO(
                accountSave.getId(),
                accountSave.getName(),
                accountSave.getValue(),
                accountSave.getCreateDate().toString(),
                accountSave.getType()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(accountSaveDTO);
    }
}
