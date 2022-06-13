package com.mendel.transaction.service;

import java.util.List;

import com.mendel.transaction.dto.TransactionAmountDTO;
import com.mendel.transaction.dto.TransactionRequestDTO;
import com.mendel.transaction.model.Transaction;

public interface TransactionService {

    Transaction createTransaction(final TransactionRequestDTO transactionRequestDTO, final Long id);

    TransactionAmountDTO getTransactionAmountById(final Long id);

    List<Long> getTransactionsByType(final String type);
}
