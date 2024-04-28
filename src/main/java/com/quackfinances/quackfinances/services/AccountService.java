package com.quackfinances.quackfinances.services;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.TransactionRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.view.controller.dto.AccountUserLoginDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository repository;
    private final UserRepository userRepository;

    public AccountService(TransactionRepository transactionRepository, AccountRepository repository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<AccountUserLoginDTO> getAccountUserLogin() throws NullPointerException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Account> accountList = repository.findAll();
        List<Account> accounts = new ArrayList<>();

        for (Account account : accountList) {
            if (account != null && account.getUser() != null && account.getUser().getName() != null) {
                if (account.getUser().getEmail().equals(userId)) {
                    accounts.add(account);
                }
            }
        }

        List<AccountUserLoginDTO> accountDTOs = new ArrayList<>();

        for (Account account : accounts) {
            AccountUserLoginDTO accountDTO = new AccountUserLoginDTO(account.getId(), account.getName(), account.getValue(), account.getCreateDate().toString(), account.getType());
            accountDTOs.add(accountDTO);
        }
        return accountDTOs;
    }



}
