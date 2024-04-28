package com.quackfinances.quackfinances.view.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public record TransactionResponseDTO(String description,
                                     BigDecimal value,
                                     com.quackfinances.quackfinances.model.TransactionType transactionType,
                                     String identifier,
                                     Optional<AccountTransferResponseDTO> sourceAccount,
                                     Optional<AccountTransferResponseDTO> destinationAccount,
                                     String createDate
) {
}
