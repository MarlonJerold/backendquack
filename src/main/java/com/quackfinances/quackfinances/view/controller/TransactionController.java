package com.quackfinances.quackfinances.view.controller;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.model.TransactionModel;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.services.TransactionService;
import com.quackfinances.quackfinances.services.service.AccountServiceInterface;
import com.quackfinances.quackfinances.view.controller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final AccountRepository repository;
    private final UserRepository userRepository;

    @Autowired(required = false)
    private AccountServiceInterface accountService;

    private final TransactionService transactionService;

    private List<Account> accountList = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, AccountRepository repository, UserRepository userRepository, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.repository = repository;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }

    public String transactionAccount(@RequestBody TransactionModel transactionModel) {
        List<Account> accountList = repository.findAll();
        return "teste";
    }

    @GetMapping
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

    @Transactional
    @PutMapping
    public Object receiveExternalValue(@RequestBody TransactionModel transactionModel) throws Exception {
        return transactionService.transaction(transactionModel);
    }

    @GetMapping("category")
    public List<CategotyValueDTO> consultegoriaCatedoriasValue () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<AccountUserLoginDTO> accountListLogin = accountService.getAccountUserLogin();

        List<List<Optional<TransactionModel>>> transactionsPerAccount = new ArrayList<>();
        for (AccountUserLoginDTO account : accountListLogin) {
            List<Optional<TransactionModel>> transactions = transactionRepository.findBySourceAccountId(account.id());
            transactionsPerAccount.add(transactions);
        }

        Map<String, BigDecimal> categoryTotals = new HashMap<>();

        for (List<Optional<TransactionModel>> transactionsPerAccounts : transactionsPerAccount) {
            for (Optional<TransactionModel> transaction : transactionsPerAccounts) {
                if (transaction.isPresent()) {
                    String category = transaction.get().getCategory();
                    BigDecimal valorTranscao = transaction.get().getValue();
                    BigDecimal currentTotal = categoryTotals.getOrDefault(category, BigDecimal.ZERO);
                    BigDecimal newTotal = currentTotal.add(valorTranscao);
                    categoryTotals.put(category, newTotal);
                }
            }
        }

        List<TransactionModel> categoryTransactions = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : categoryTotals.entrySet()) {
            TransactionModel categoryTransaction = new TransactionModel(entry.getKey(), entry.getValue());
            categoryTransactions.add(categoryTransaction);
        }

        List<CategotyValueDTO> categotyValueDTOS = new ArrayList<>();
        for (TransactionModel transactionModel : categoryTransactions) {

            CategotyValueDTO accountDTO = new CategotyValueDTO(transactionModel.getCategory(), transactionModel.getValue().doubleValue());
            categotyValueDTOS.add(accountDTO);
        }

        return categotyValueDTOS;
    }

    @GetMapping("lista")
    public List<TransactionResponseDTO> getListTransaction() {
        return transactionService.getListTransactions();
    }
}
