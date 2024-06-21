package com.quackfinances.quackfinances.services.service.impl;

import com.quackfinances.quackfinances.enums.AccountEnum;
import com.quackfinances.quackfinances.model.User;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.services.service.AccountService;
import com.quackfinances.quackfinances.dto.Account.AccountCreateDTO;
import com.quackfinances.quackfinances.dto.Account.AccountUserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AccountUserLoginDTO> getAccountUserLogin() throws NullPointerException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<com.quackfinances.quackfinances.model.Account> accountList = accountRepository.findAll();
        List<com.quackfinances.quackfinances.model.Account> accounts = new ArrayList<>();

        for (com.quackfinances.quackfinances.model.Account account : accountList) {
            if (account != null && account.getUser() != null && account.getUser().getName() != null) {
                if (account.getUser().getEmail().equals(userId)) {
                    accounts.add(account);
                }
            }
        }

        List<AccountUserLoginDTO> accountDTOs = new ArrayList<>();

        for (com.quackfinances.quackfinances.model.Account account : accounts) {
            AccountUserLoginDTO accountDTO = new AccountUserLoginDTO(account.getId(), account.getName(), account.getValue(), account.getCreateDate().toString(), account.getType());
            accountDTOs.add(accountDTO);
        }
        return accountDTOs;
    }

    @Override
    public List<com.quackfinances.quackfinances.model.Account> getAll() throws NullPointerException {
        List<com.quackfinances.quackfinances.model.Account> accountList = accountRepository.findAll();
        return accountList;
    }

    @Override
    public com.quackfinances.quackfinances.model.Account createAccount(AccountCreateDTO account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();

        AccountEnum accountEnumType = AccountEnum.valueOf (account.accountType());

        if (account != null) {
            Optional<User> userBusca = userRepository.findByEmail(user);
            com.quackfinances.quackfinances.model.Account accountEntity = new com.quackfinances.quackfinances.model.Account();
            accountEntity.setName(account.name());
            accountEntity.setUser(userBusca.get());
            accountEntity.setType(accountEnumType);

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
