package com.quackfinances.quackfinances.services;

import com.quackfinances.quackfinances.exceptions.AccountNotFoundException;
import com.quackfinances.quackfinances.exceptions.InsufficientBalanceException;
import com.quackfinances.quackfinances.exceptions.PermissionDeniedException;
import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.model.TransactionModel;
import com.quackfinances.quackfinances.model.TransactionType;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.utils.DateTimeUtils;
import com.quackfinances.quackfinances.view.controller.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository repository;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository repository, AccountService accountService, CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.repository = repository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    public List<TransactionResponseDTO> getListTransactions() {
        List<AccountUserLoginDTO> accountListLogin = accountService.getAccountUserLogin();
        List<List<Optional<TransactionModel>>> transactionsPerAccount = new ArrayList<>();

        for (AccountUserLoginDTO account : accountListLogin) {
            List<Optional<TransactionModel>> transactions = transactionRepository.findBySourceAccountId(account.id());
            transactionsPerAccount.add(transactions);
        }

        List<List<TransactionResponseDTO>> retorno = new ArrayList<>();

        for (List<Optional<TransactionModel>> transactions : transactionsPerAccount) {
            List<TransactionResponseDTO> transactionsDTO = new ArrayList<>();
            for (Optional<TransactionModel> transaction : transactions) {

                Optional<AccountTransferResponseDTO> sourceAccountDTO = getAccountTransferResponseDTOById(transaction.get().getSourceAccount());
                Optional<AccountTransferResponseDTO> destinationAccountDTO = getAccountTransferResponseDTOById(transaction.get().getDestinationAccountId());

                DateTimeFormatter formatter = DateTimeUtils.FORMATTER;
                String createDateFormatada = transaction.get().getCreateDate().format(formatter);

                TransactionResponseDTO transactionDTO = new TransactionResponseDTO(
                        transaction.get().getDescription(),
                        transaction.get().getValue(),
                        transaction.get().getTransactionType(),
                        transaction.get().getIdentifier(),
                        sourceAccountDTO,
                        destinationAccountDTO,
                        createDateFormatada
                );
                transactionsDTO.add(transactionDTO);
            }
            retorno.add(transactionsDTO);
        }

        List<TransactionResponseDTO> flatList = retorno.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return flatList;
    }


    @Transactional(rollbackFor = Exception.class)
    public Object transaction (TransactionModel transactionModel) throws Exception, AccountNotFoundException, PermissionDeniedException, InsufficientBalanceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> sourceAccount = repository.findById(transactionModel.getSourceAccount());

        try {
            if (sourceAccount.isEmpty()) throw new AccountNotFoundException();

            if (!sourceAccount.get().getUser().getEmail().equals(authentication.getName())) throw new PermissionDeniedException();

            if (transactionModel.getTransactionType() == TransactionType.TRANSFER) {
                Optional<Account> destinationAccount = repository.findById(transactionModel.getDestinationAccount());

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
            if (transactionModel.getTransactionType() == TransactionType.EXPENSE) {
                if (sourceAccount.isPresent() && sourceAccount.get().getUser() != null && sourceAccount.get().getUser().getEmail().equals(authentication.getName())) {
                    TransactionDTO transactionDTO = transactionExpense(transactionModel);
                    return ResponseEntity.ok(transactionDTO);

                } else {
                    throw new Exception("ID da conta não faz parte das contas do usuário");
                }
            }
        } catch (AccountNotFoundException | PermissionDeniedException | InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro durante a transação");
        }
        return null;
    }

    public Optional<AccountTransferResponseDTO> getAccountTransferResponseDTOById(Integer accountId) {
        Optional<Account> accountOptional = repository.findById(accountId);
        return accountOptional.map(account ->
                new AccountTransferResponseDTO(account.getName(), account.getType().toString())
        );
    }

    public TransactionDTO transactionExpense(TransactionModel transactionModel) {

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
                return transactionDTO;
            }
        } return null;
    }
}





