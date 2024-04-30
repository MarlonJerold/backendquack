package com.quackfinances.quackfinances.services.service;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.view.controller.dto.AccountCreateDTO;
import com.quackfinances.quackfinances.view.controller.dto.AccountUserLoginDTO;
import org.springframework.stereotype.Service;


import java.util.List;


public interface AccountServiceInterface {

    List<AccountUserLoginDTO> getAccountUserLogin();

    List<Account> getAll();
    Account createAccount(AccountCreateDTO account);

}
