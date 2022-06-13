package com.mendel.transaction.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.mendel.transaction.model.Transaction;

public class TransactionNode {
    private final Transaction currentTransaction;

    private final HashSet<Long> transactionList;

    public TransactionNode(final Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
        transactionList = new HashSet<>();
    }

    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction.getId());
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public HashSet<Long> getTransactionList() {
        return transactionList;
    }
}
