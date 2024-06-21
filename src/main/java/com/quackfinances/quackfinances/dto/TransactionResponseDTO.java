package com.quackfinances.quackfinances.dto;

import com.quackfinances.quackfinances.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Optional;

public record TransactionResponseDTO(String description,
                                     BigDecimal value,
                                     TransactionType transactionType,
                                     String identifier,
                                     Optional<AccountTransferResponseDTO> sourceAccount,
                                     Optional<AccountTransferResponseDTO> destinationAccount,
                                     String createDate
) {
}
