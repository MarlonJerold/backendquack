package com.quackfinances.quackfinances.dto.Transaction;

import com.quackfinances.quackfinances.dto.Account.AccountTransferResponseDTO;
import com.quackfinances.quackfinances.enums.TransactionEnum;

import java.math.BigDecimal;
import java.util.Optional;

public record TransactionResponseDTO(String description,
                                     BigDecimal value,
                                     TransactionEnum transactionEnum,
                                     String identifier,
                                     Optional<AccountTransferResponseDTO> sourceAccount,
                                     Optional<AccountTransferResponseDTO> destinationAccount,
                                     String createDate
) {
}
