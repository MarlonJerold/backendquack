package com.quackfinances.quackfinances.services.strategy;

import com.quackfinances.quackfinances.exceptions.AccountNotFoundException;
import com.quackfinances.quackfinances.exceptions.InsufficientBalanceException;
import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.model.Transaction;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.dto.Account.AccountUserLoginDTO;
import com.quackfinances.quackfinances.services.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class TransferTransactionStrategy implements TransactionStrategy{

    @Override
    public ResponseEntity<?> execute(Transaction transaction, Authentication authentication, AccountRepository repository, TransactionRepository transactionRepository, CategoryService categoryService) throws Exception {
        Optional<Account> sourceAccount = repository.findById(transaction.getSourceAccount().longValue());
        Optional<Account> destinationAccount = repository.findById(transaction.getDestinationAccountId().longValue());
        if (destinationAccount.isEmpty()) throw new AccountNotFoundException();

        if (transaction.getValue().compareTo(sourceAccount.get().getValue()) > 0) throw new InsufficientBalanceException();

        sourceAccount.get().setValue(sourceAccount.get().getValue().subtract(transaction.getValue()));
        destinationAccount.get().setValue(destinationAccount.get().getValue().add(transaction.getValue()));

        repository.save(destinationAccount.get());
        transactionRepository.save(transaction);
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
