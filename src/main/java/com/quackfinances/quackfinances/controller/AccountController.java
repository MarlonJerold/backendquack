package com.quackfinances.quackfinances.controller;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.services.service.AccountService;
import com.quackfinances.quackfinances.dto.Account.AccountCreateDTO;
import com.quackfinances.quackfinances.dto.Account.AccountUserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/account")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll(){
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/account")
    public ResponseEntity<List<AccountUserLoginDTO>> getAccountUserLogin() {
        return ResponseEntity.ok(accountService.getAccountUserLogin());
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountCreateDTO account) {
       return  ResponseEntity.ok(accountService.createAccount(account));
    }

}
