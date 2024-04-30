package com.quackfinances.quackfinances.services.service.impl;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.model.AccountType;
import com.quackfinances.quackfinances.model.UserModel;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.services.service.AccountServiceInterface;
import com.quackfinances.quackfinances.view.controller.dto.AccountCreateDTO;
import com.quackfinances.quackfinances.view.controller.dto.AccountUserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountServiceInterface {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AccountUserLoginDTO> getAccountUserLogin() throws NullPointerException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Account> accountList = accountRepository.findAll();
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

    @Override
    public List<Account> getAll() throws NullPointerException {
        List<Account> accountList = accountRepository.findAll();
        return accountList;
    }

    @Override
    public Account createAccount(AccountCreateDTO account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();

        AccountType accountType = AccountType.valueOf (account.accountType());

        if (account != null) {
            Optional<UserModel> userBusca = userRepository.findByEmail(user);
            Account accountEntity = new Account();
            accountEntity.setName(account.name());
            accountEntity.setUser(userBusca.get());
            accountEntity.setType(accountType);

            if (account.accountType() == null) {
                System.out.println("Deu erro aqui");
            }

            accountEntity.setValue(account.value());
            return accountRepository.save(accountEntity);

        } else {
            return null;
        }
    }
}
