package com.quackfinances.quackfinances.dto.Transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quackfinances.quackfinances.dto.Account.AccountTransferResponseDTO;
import com.quackfinances.quackfinances.enums.TransactionEnum;

import java.math.BigDecimal;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public record TransactionResponseDTO(String description,
                                     BigDecimal value,
                                     TransactionEnum transactionEnum,
                                     String identifier,
                                     Optional<AccountTransferResponseDTO> sourceAccount,
                                     Optional<AccountTransferResponseDTO> destinationAccount,
                                     String createDate
) {

    public TransactionResponseDTO(
            String description,
            BigDecimal value,
            TransactionEnum transactionEnum,
            String identifier,
            Optional<AccountTransferResponseDTO> sourceAccount,
            String createDate
    ) {
        this(description, value, transactionEnum, identifier, sourceAccount, Optional.empty(), createDate);
    }

}
