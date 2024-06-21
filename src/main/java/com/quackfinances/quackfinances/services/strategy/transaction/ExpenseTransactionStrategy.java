package com.quackfinances.quackfinances.services.strategy.transaction;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.model.Transaction;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.dto.Account.AccountCreateDTO;
import com.quackfinances.quackfinances.dto.Categoty.CategoryRequestDTO;
import com.quackfinances.quackfinances.dto.Transaction.TransactionDTO;
import com.quackfinances.quackfinances.services.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ExpenseTransactionStrategy implements TransactionStrategy{

    private final CategoryService categoryService;

    public ExpenseTransactionStrategy(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public ResponseEntity<?> execute(Transaction transaction, AccountRepository repository, TransactionRepository transactionRepository) throws Exception {

        Optional<Account> sourceAccount = repository.findById(transaction.getSourceAccount());
        List<CategoryRequestDTO> categoryRequestDTOList = categoryService.getCategory();

        for (CategoryRequestDTO categoryRequestDTO: categoryRequestDTOList ) {
            if (transaction.getCategory().toString().equals(categoryRequestDTO.categoryName())) {
                Account accountNewValue = sourceAccount.get();
                BigDecimal accountValues = accountNewValue.getValue();
                BigDecimal requestValue = transaction.getValue();

                BigDecimal newSourceAccountValue = accountValues.subtract(requestValue);
                accountNewValue.setValue(newSourceAccountValue);

                repository.save(accountNewValue);

                Transaction transacation = transactionRepository.save(transaction);
                Optional<Account> sourceAccountTransaction =  repository.findById(transacation.getSourceAccount());

                Optional<AccountCreateDTO> sourceAccountTransactionDTO = Optional.of(new AccountCreateDTO(
                        sourceAccountTransaction.get().getName(),
                        sourceAccountTransaction.get().getValue(),
                        sourceAccountTransaction.get().getType().toString()
                ));

                TransactionDTO transactionDTO = new TransactionDTO(
                        transacation.getDescription(),
                        transacation.getValue(),
                        transacation.getTransactionEnum(),
                        transacation.getIdentifier(),
                        sourceAccountTransactionDTO
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(transactionDTO);
            }
        } return null;

    }
}
