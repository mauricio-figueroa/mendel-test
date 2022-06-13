package com.mendel.transaction.repository;

import java.util.List;

import com.mendel.transaction.exception.TransactionAlreadyExistException;
import com.mendel.transaction.model.Transaction;
import com.mendel.transaction.service.TransactionNode;

public interface TransactionRepository {

    TransactionNode save(final Transaction transaction) throws TransactionAlreadyExistException;

    TransactionNode get(final Long transactionID);

    List<Transaction> getTransactionsByType(final String type);
}
