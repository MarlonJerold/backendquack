package com.quackfinances.quackfinances.services.service;

import com.quackfinances.quackfinances.dto.Account.AccountTransferResponseDTO;
import com.quackfinances.quackfinances.dto.Categoty.CategotyValueDTO;
import com.quackfinances.quackfinances.dto.Transaction.TransactionDTO;
import com.quackfinances.quackfinances.dto.Transaction.TransactionResponseDTO;
import com.quackfinances.quackfinances.exceptions.AccountNotFoundException;
import com.quackfinances.quackfinances.exceptions.InsufficientBalanceException;
import com.quackfinances.quackfinances.exceptions.PermissionDeniedException;
import com.quackfinances.quackfinances.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<TransactionResponseDTO> getListTransactions();
    Object transaction(Transaction transaction) throws Exception, AccountNotFoundException, PermissionDeniedException, InsufficientBalanceException;
    Optional<AccountTransferResponseDTO> getAccountTransferResponseDTOById(Integer accountId);
    TransactionDTO transactionExpense(Transaction transaction);
    BigDecimal totalAccountValue();
    List<CategotyValueDTO> consultegoriaCatedoriasValue ();
}
