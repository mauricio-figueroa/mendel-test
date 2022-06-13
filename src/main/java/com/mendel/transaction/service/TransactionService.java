package com.mendel.transaction.service;

import com.mendel.transaction.dto.TransactionAmountDTO;
import com.mendel.transaction.dto.TransactionRequestDTO;
import com.mendel.transaction.model.Transaction;

public interface TransactionService {

    Transaction createTransaction(final TransactionRequestDTO transactionRequestDTO, final Long id);

    TransactionAmountDTO getTransactionAmountById(final Long id);
}
