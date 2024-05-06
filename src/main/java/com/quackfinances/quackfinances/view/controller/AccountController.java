package com.quackfinances.quackfinances.view.controller;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.services.service.AccountServiceInterface;
import com.quackfinances.quackfinances.view.controller.dto.AccountCreateDTO;
import com.quackfinances.quackfinances.view.controller.dto.AccountUserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/account")
@RestController
public class AccountController {

    @Autowired(required = false)
    private AccountServiceInterface accountServiceInterface;

    @GetMapping
    public ResponseEntity<List<Account>> getAll(){
        return ResponseEntity.ok(accountServiceInterface.getAll());
    }

    @GetMapping("/account")
    public ResponseEntity<List<AccountUserLoginDTO>> getAccountUserLogin() {
        return ResponseEntity.ok(accountServiceInterface.getAccountUserLogin());
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountCreateDTO account) {
       return  ResponseEntity.ok(accountServiceInterface.createAccount(account));
    }

}
