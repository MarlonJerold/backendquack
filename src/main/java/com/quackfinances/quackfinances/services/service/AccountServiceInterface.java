package com.quackfinances.quackfinances.services.service;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.dto.AccountCreateDTO;
import com.quackfinances.quackfinances.dto.AccountUserLoginDTO;


import java.util.List;


public interface AccountServiceInterface {

    List<AccountUserLoginDTO> getAccountUserLogin();
    List<Account> getAll();
    Account createAccount(AccountCreateDTO account);

}
