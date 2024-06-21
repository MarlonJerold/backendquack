package com.quackfinances.quackfinances.controller;

import com.quackfinances.quackfinances.dto.CategotyValueDTO;
import com.quackfinances.quackfinances.dto.TransactionResponseDTO;
import com.quackfinances.quackfinances.model.TransactionModel;
import com.quackfinances.quackfinances.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
