package com.quackfinances.quackfinances.controller;

import com.quackfinances.quackfinances.dto.Categoty.CategotyValueDTO;
import com.quackfinances.quackfinances.dto.Transaction.TransactionResponseDTO;
import com.quackfinances.quackfinances.model.Transaction;
import com.quackfinances.quackfinances.services.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<BigDecimal> totalAccountValue() {
        return ResponseEntity.ok(transactionService.totalAccountValue());
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Object> receiveExternalValue(@RequestBody Transaction transaction) throws Exception {
        return ResponseEntity.ok(transactionService.transaction(transaction));
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
