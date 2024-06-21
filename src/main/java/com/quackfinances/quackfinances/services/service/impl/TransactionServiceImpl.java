package com.quackfinances.quackfinances.services.service.impl;

import com.quackfinances.quackfinances.dto.Account.AccountCreateDTO;
import com.quackfinances.quackfinances.dto.Account.AccountTransferResponseDTO;
import com.quackfinances.quackfinances.dto.Account.AccountUserLoginDTO;
import com.quackfinances.quackfinances.dto.Categoty.CategoryRequestDTO;
import com.quackfinances.quackfinances.dto.Categoty.CategotyValueDTO;
import com.quackfinances.quackfinances.dto.Transaction.TransactionDTO;
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
import com.quackfinances.quackfinances.services.strategy.ExpenseTransactionStrategy;
import com.quackfinances.quackfinances.services.strategy.TransactionStrategy;
import com.quackfinances.quackfinances.services.strategy.TransferTransactionStrategy;
import com.quackfinances.quackfinances.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Override
    public List<TransactionResponseDTO> getListTransactions() {
        List<AccountUserLoginDTO> accountListLogin = accountService.getAccountUserLogin();
        List<List<Optional<com.quackfinances.quackfinances.model.Transaction>>> transactionsPerAccount = new ArrayList<>();

        for (AccountUserLoginDTO account : accountListLogin) {
            List<Optional<com.quackfinances.quackfinances.model.Transaction>> transactions = transactionRepository.findBySourceAccountId(account.id());
            transactionsPerAccount.add(transactions);
        }

        List<List<TransactionResponseDTO>> retorno = new ArrayList<>();

        for (List<Optional<Transaction>> transactions : transactionsPerAccount) {
            List<TransactionResponseDTO> transactionsDTO = new ArrayList<>();
            for (Optional<com.quackfinances.quackfinances.model.Transaction> transaction : transactions) {

                Optional<AccountTransferResponseDTO> sourceAccountDTO = getAccountTransferResponseDTOById(transaction.get().getSourceAccount());
                Optional<AccountTransferResponseDTO> destinationAccountDTO = getAccountTransferResponseDTOById(transaction.get().getDestinationAccountId());

                DateTimeFormatter formatter = DateTimeUtils.FORMATTER;
                String createDateFormatada = transaction.get().getCreateDate().format(formatter);

                TransactionResponseDTO transactionDTO = new TransactionResponseDTO(
                        transaction.get().getDescription(),
                        transaction.get().getValue(),
                        transaction.get().getTransactionEnum(),
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object transaction( Transaction transaction ) throws Exception, AccountNotFoundException, PermissionDeniedException, InsufficientBalanceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> sourceAccount = repository.findById(transaction.getSourceAccount());
        TransactionStrategy transactionStrategy = new TransferTransactionStrategy();

        try {
            if (sourceAccount.isEmpty()) throw new AccountNotFoundException();

            if (!sourceAccount.get().getUser().getEmail().equals(authentication.getName())) throw new PermissionDeniedException();

            if (transaction.getTransactionEnum() == TransactionEnum.TRANSFER) {
                transactionStrategy = new TransferTransactionStrategy();
            }

            if (transaction.getTransactionEnum() == TransactionEnum.EXPENSE) {
                if (sourceAccount.isPresent() && sourceAccount.get().getUser() != null && sourceAccount.get().getUser().getEmail().equals(authentication.getName())) {
                    ExpenseTransactionStrategy expenseTransactionStrategy;
                    expenseTransactionStrategy = new  ExpenseTransactionStrategy(categoryService);
                    return expenseTransactionStrategy.execute(transaction, authentication, repository, transactionRepository, categoryService);

                } else {
                    throw new Exception("ID da conta não faz parte das contas do usuário");
                }
            }
            return transactionStrategy.execute(transaction, authentication, repository, transactionRepository, categoryService);

        } catch (AccountNotFoundException | PermissionDeniedException | InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro durante a transação");
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
    public TransactionDTO transactionExpense(Transaction transaction) {

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

                com.quackfinances.quackfinances.model.Transaction transacation = transactionRepository.save(transaction);
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
                return transactionDTO;
            }
        } return null;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<AccountUserLoginDTO> accountListLogin = accountService.getAccountUserLogin();

        List<List<Optional<Transaction>>> transactionsPerAccount = new ArrayList<>();

        for (AccountUserLoginDTO account : accountListLogin) {
            List<Optional<Transaction>> transactions = transactionRepository.findBySourceAccountId(account.id());
            transactionsPerAccount.add(transactions);
        }

        Map<String, BigDecimal> categoryTotals = new HashMap<>();

        for (List<Optional<Transaction>> transactionsPerAccounts : transactionsPerAccount) {
            for (Optional<Transaction> transaction : transactionsPerAccounts) {
                if (transaction.isPresent()) {
                    String category = transaction.get().getCategory();
                    BigDecimal valorTranscao = transaction.get().getValue();
                    BigDecimal currentTotal = categoryTotals.getOrDefault(category, BigDecimal.ZERO);
                    BigDecimal newTotal = currentTotal.add(valorTranscao);
                    categoryTotals.put(category, newTotal);
                }
            }
        }

        List<Transaction> categoryTransactions = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : categoryTotals.entrySet()) {
            Transaction categoryTransaction = new Transaction(entry.getKey(), entry.getValue());
            categoryTransactions.add(categoryTransaction);
        }

        List<CategotyValueDTO> categotyValueDTOS = new ArrayList<>();
        for (Transaction transaction : categoryTransactions) {
            CategotyValueDTO accountDTO = new CategotyValueDTO(transaction.getCategory(), transaction.getValue().doubleValue());
            categotyValueDTOS.add(accountDTO);
        }
        return categotyValueDTOS;
    }

}





