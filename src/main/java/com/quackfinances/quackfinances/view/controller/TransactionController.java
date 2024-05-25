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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<BigDecimal> totalAccountValue() {
        return ResponseEntity.ok(transactionService.totalAccountValue());
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Object> receiveExternalValue(@RequestBody TransactionModel transactionModel) throws Exception {
        return ResponseEntity.ok(transactionService.transaction(transactionModel));
    }

    @GetMapping("category")
    public ResponseEntity<List<CategotyValueDTO>> consultegoriaCatedoriasValue () {
        return ResponseEntity.ok(transactionService.consultegoriaCatedoriasValue());
    }

    @GetMapping("lista")
    public ResponseEntity<List<TransactionResponseDTO>> getListTransaction() {
        return ResponseEntity.ok(transactionService.getListTransactions());
    }
}
