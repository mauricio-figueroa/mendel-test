package com.mendel.transaction.repository;

import java.util.List;

import com.mendel.transaction.model.Transaction;

public interface TransactionRepository {

    Transaction save(final Transaction transaction);

    Transaction get(final Long transactionID);

    List<Transaction> getTransactionsByType(final String type);
}
