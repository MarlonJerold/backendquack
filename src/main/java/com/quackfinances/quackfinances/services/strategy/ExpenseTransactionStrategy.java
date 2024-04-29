package com.quackfinances.quackfinances.services.strategy;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.model.TransactionModel;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.services.CategoryService;
import com.quackfinances.quackfinances.view.controller.dto.AccountCreateDTO;
import com.quackfinances.quackfinances.view.controller.dto.CategoryRequestDTO;
import com.quackfinances.quackfinances.view.controller.dto.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ExpenseTransactionStrategy implements TransactionStrategy{

    private final CategoryService categoryService;

    public ExpenseTransactionStrategy(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<?> execute(TransactionModel transactionModel, Authentication authentication, AccountRepository repository, TransactionRepository transactionRepository, CategoryService categoryService) throws Exception {

        Optional<Account> sourceAccount = repository.findById(transactionModel.getSourceAccount());
        List<CategoryRequestDTO> categoryRequestDTOList = categoryService.getCategory();

        for (CategoryRequestDTO categoryRequestDTO: categoryRequestDTOList ) {
            if (transactionModel.getCategory().toString().equals(categoryRequestDTO.categoryName())) {
                Account accountNewValue = sourceAccount.get();
                BigDecimal accountValues = accountNewValue.getValue();
                BigDecimal requestValue = transactionModel.getValue();

                BigDecimal newSourceAccountValue = accountValues.subtract(requestValue);
                accountNewValue.setValue(newSourceAccountValue);

                repository.save(accountNewValue);

                TransactionModel transacation = transactionRepository.save(transactionModel);
                Optional<Account> sourceAccountTransaction =  repository.findById(transacation.getSourceAccount());

                Optional<AccountCreateDTO> sourceAccountTransactionDTO = Optional.of(new AccountCreateDTO(
                        sourceAccountTransaction.get().getName(),
                        sourceAccountTransaction.get().getValue(),
                        sourceAccountTransaction.get().getType().toString()
                ));

                TransactionDTO transactionDTO = new TransactionDTO(
                        transacation.getDescription(),
                        transacation.getValue(),
                        transacation.getTransactionType(),
                        transacation.getIdentifier(),
                        sourceAccountTransactionDTO
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(transactionDTO);
            }
        } return null;

    }
}
