package com.mendel.transaction.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.mendel.transaction.exception.TransactionAlreadyExistException;
import com.mendel.transaction.model.Transaction;

@Service
public class TransactionRepositoryImpl implements TransactionRepository {

    private static final Map<Long, Transaction> TRANSACTION_MAP = new HashMap<>();
    private static final Map<String, List<Transaction>> TRANSACTION_TYPE_MAP = new HashMap<>();

    @Override
    public Transaction save(final Transaction transaction) throws TransactionAlreadyExistException {
        if (TRANSACTION_MAP.get(transaction.getId()) != null) {
            throw TransactionAlreadyExistException.create();
        }
        addToTransactionTypeMap(transaction);
        TRANSACTION_MAP.put(transaction.getId(), transaction);
        return TRANSACTION_MAP.get(transaction.getId());
    }


    @Override
    public Transaction get(final Long transactionID) {
        return TRANSACTION_MAP.get(transactionID);
    }

    @Override
    public List<Transaction> getTransactionsByType(final String type) {
        final List<Transaction> transactions = TRANSACTION_TYPE_MAP.get(type);
        if (transactions == null) {
            return List.of();
        }
        return transactions;
    }


    private void addToTransactionTypeMap(final Transaction transaction) {
        List<Transaction> transactions = TRANSACTION_TYPE_MAP.get(transaction.getType());
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
        TRANSACTION_TYPE_MAP.put(transaction.getType(), transactions);
    }

}
