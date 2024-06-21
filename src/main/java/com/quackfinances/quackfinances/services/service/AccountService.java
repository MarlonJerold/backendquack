package com.quackfinances.quackfinances.services.service;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.dto.Account.AccountCreateDTO;
import com.quackfinances.quackfinances.dto.Account.AccountUserLoginDTO;


import java.util.List;


public interface AccountService {

    List<AccountUserLoginDTO> getAccountUserLogin();
    List<Account> getAll();
    Account createAccount(AccountCreateDTO account);

}
