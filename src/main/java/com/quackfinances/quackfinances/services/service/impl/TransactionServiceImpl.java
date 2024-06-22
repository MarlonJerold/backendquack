package com.quackfinances.quackfinances.services.service.impl;

import com.quackfinances.quackfinances.dto.Account.AccountTransferResponseDTO;
import com.quackfinances.quackfinances.dto.Account.AccountUserLoginDTO;
import com.quackfinances.quackfinances.dto.Categoty.CategotyValueDTO;
import com.quackfinances.quackfinances.dto.Transaction.TransactionResponseDTO;
import com.quackfinances.quackfinances.exceptions.AccountNotFoundException;
import com.quackfinances.quackfinances.exceptions.InsufficientBalanceException;
import com.quackfinances.quackfinances.exceptions.PermissionDeniedException;
import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.enums.TransactionEnum;
import com.quackfinances.quackfinances.model.Transaction;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.services.service.AccountService;
import com.quackfinances.quackfinances.services.service.CategoryService;
import com.quackfinances.quackfinances.services.service.TransactionService;
import com.quackfinances.quackfinances.services.strategy.transaction.ExpenseTransactionStrategy;
import com.quackfinances.quackfinances.services.strategy.transaction.TransactionStrategy;
import com.quackfinances.quackfinances.services.strategy.transaction.TransferTransactionStrategy;
import com.quackfinances.quackfinances.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository repository;

    @Autowired(required = false)
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;

    private List<Account> accountList = Collections.synchronizedList(new ArrayList<>());
    private DateTimeFormatter dateTimeFormatter = DateTimeUtils.FORMATTER;


    @Override
    public List<TransactionResponseDTO> getListTransactions() {
        List<AccountUserLoginDTO> accountListLogin = accountService.getAccountUserLogin();
        List<List<Optional<Transaction>>> transactionsPerAccount = new ArrayList<>();

        for (AccountUserLoginDTO account : accountListLogin) {
            List<Optional<Transaction>> transactions = transactionRepository.findBySourceAccountId(account.id());
            transactionsPerAccount.add(transactions);
        }

        List<List<TransactionResponseDTO>> retorno = new ArrayList<>();

        for (List<Optional<Transaction>> transactions : transactionsPerAccount) {
            List<TransactionResponseDTO> transactionsDTO = new ArrayList<>();
            for (Optional<Transaction> transaction : transactions) {

                if (transaction.get().getTransactionEnum() == TransactionEnum.TRANSFER) {
                    transactionsDTO.add(parseTransactionForResponseDTOInTransfer(transaction));
                }
                if (transaction.get().getTransactionEnum() == TransactionEnum.EXPENSE) {
                    transactionsDTO.add(parseTransactionForResponseDTOinExpense(transaction));
                }
            }
            retorno.add(transactionsDTO);
        }

        List<TransactionResponseDTO> flatList = retorno.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return flatList;
    }

    private TransactionResponseDTO parseTransactionForResponseDTOInTransfer(Optional<Transaction> transaction) {
        return new TransactionResponseDTO(
                transaction.get().getDescription(),
                transaction.get().getValue(),
                transaction.get().getTransactionEnum(),
                transaction.get().getIdentifier(),
                getAccountTransferResponseDTOById(transaction.get().getSourceAccount()),
                getAccountTransferResponseDTOById(transaction.get().getDestinationAccountId()),
                transaction.get().getCreateDate().format(dateTimeFormatter)
        );
    }

    private TransactionResponseDTO parseTransactionForResponseDTOinExpense(Optional<Transaction> transaction) {
        return new TransactionResponseDTO(
                transaction.get().getDescription(),
                transaction.get().getValue(),
                transaction.get().getTransactionEnum(),
                transaction.get().getIdentifier(),
                getAccountTransferResponseDTOById(transaction.get().getSourceAccount()),
                transaction.get().getCreateDate().format(dateTimeFormatter)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object transaction(Transaction transaction) {
        try {

            TransactionStrategy transactionStrategy = determineTransactionStrategy(transaction);
            Optional<Account> sourceAccount = findSourceAccount(transaction);

            return transactionStrategy != null ?
                    executeTransactionStrategy(transactionStrategy, transaction, sourceAccount) :
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tipo de transação indisponível");

        } catch (Exception e) {
            return handleTransactionException(e);
        }
    }

    @Override
    public Optional<AccountTransferResponseDTO> getAccountTransferResponseDTOById(Integer accountId) {
        Optional<Account> accountOptional = repository.findById(accountId);
        return accountOptional.map(account ->
                new AccountTransferResponseDTO(account.getName(), account.getType().toString())
        );
    }

    @Override
    public BigDecimal totalAccountValue() {
        BigDecimal totalValue = BigDecimal.ZERO;
        List<Account> accounts;
        synchronized (accountList) {
            accounts = repository.findAll();
            accountList.clear();
            accountList.addAll(accounts);
        }

        for (Account account : accountList) {
            totalValue = totalValue.add(account.getValue());
        }
        return totalValue;
    }

    @Override
    public List<CategotyValueDTO> consultegoriaCatedoriasValue () {
        List<AccountUserLoginDTO> accountListLogin = accountService.getAccountUserLogin();

        List<List<Optional<Transaction>>> transactionsPerAccount = new ArrayList<>();
        for (AccountUserLoginDTO account : accountListLogin) {
            transactionsPerAccount.add(transactionRepository.findBySourceAccountId(account.id()));
        }

        Map<String, BigDecimal> categoryTotals = new HashMap<>();
        for (List<Optional<Transaction>> transactionsPerAccounts : transactionsPerAccount) {
            for (Optional<Transaction> transaction : transactionsPerAccounts) {
                if (transaction.isPresent()) {
                    BigDecimal currentTotal = categoryTotals.getOrDefault(transaction.get().getCategory(), BigDecimal.ZERO);
                    BigDecimal newTotal = currentTotal.add(transaction.get().getValue());
                    categoryTotals.put(transaction.get().getCategory(), newTotal);
                }
            }
        }

        List<Transaction> categoryTransactions = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : categoryTotals.entrySet()) {
            categoryTransactions.add(new Transaction(entry.getKey(), entry.getValue()));
        }

        List<CategotyValueDTO> categotyValueDTOS = new ArrayList<>();
        for (Transaction transaction : categoryTransactions) {
            categotyValueDTOS.add(new CategotyValueDTO(transaction.getCategory(), transaction.getValue().doubleValue()));
        }
        return categotyValueDTOS;
    }

    private Optional<Account> findSourceAccount(Transaction transaction) throws Exception {
        Optional<Account> sourceAccount = repository.findById(transaction.getSourceAccount());
        if (sourceAccount.isEmpty()) throw new AccountNotFoundException();
        if (!sourceAccount.get().getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) throw new PermissionDeniedException();
        return sourceAccount;
    }

    private TransactionStrategy determineTransactionStrategy(Transaction transaction) {
        switch (transaction.getTransactionEnum()) {
            case TRANSFER:
                return new TransferTransactionStrategy();
            case EXPENSE:
                return new ExpenseTransactionStrategy(categoryService);
            default:
                return null;
        }
    }

    private Object executeTransactionStrategy(TransactionStrategy strategy, Transaction transaction, Optional<Account> sourceAccount) throws Exception {

        if (strategy instanceof TransferTransactionStrategy) {
            return strategy.execute(transaction, repository, transactionRepository);
        }

        if (strategy instanceof ExpenseTransactionStrategy) {
            if (sourceAccount.isPresent() && sourceAccount.get().getUser() != null &&
                    sourceAccount.get().getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                return strategy.execute(transaction, repository, transactionRepository);
            else throw new Exception("ID da conta não faz parte das contas do usuário");

        } else {
            throw new Exception("Estratégia de transação não suportada");
        }
    }

    private ResponseEntity<Object> handleTransactionException(Exception e) {
        if (e instanceof AccountNotFoundException || e instanceof PermissionDeniedException || e instanceof InsufficientBalanceException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } else {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro durante a transação");
        }
    }

}





