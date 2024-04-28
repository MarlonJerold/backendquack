package com.quackfinances.quackfinances.view.controller.dto;

import com.quackfinances.quackfinances.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

public record TransactionDTO(String description, BigDecimal value, com.quackfinances.quackfinances.model.TransactionType transactionType, String identifier, Optional<AccountCreateDTO> sourceAccount) {
}
