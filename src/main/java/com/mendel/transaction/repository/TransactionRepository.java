package com.mendel.transaction.repository;

import com.mendel.transaction.model.Transaction;

public interface TransactionRepository {

    Transaction save(final Transaction transaction);

    Transaction get(final Long transactionID);
}
