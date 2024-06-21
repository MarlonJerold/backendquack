package com.quackfinances.quackfinances.dto.Transaction;

import com.quackfinances.quackfinances.dto.Account.AccountCreateDTO;
import com.quackfinances.quackfinances.enums.TransactionEnum;

import java.math.BigDecimal;
import java.util.Optional;

public record TransactionDTO(String description, BigDecimal value, TransactionEnum transactionEnum, String identifier, Optional<AccountCreateDTO> sourceAccount) {
}
