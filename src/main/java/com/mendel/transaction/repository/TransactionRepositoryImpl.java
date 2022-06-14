package com.mendel.transaction.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.mendel.transaction.exception.TransactionAlreadyExistException;
import com.mendel.transaction.model.Transaction;
import com.mendel.transaction.service.TransactionNode;

@Service
public class TransactionRepositoryImpl implements TransactionRepository {

    private static final Map<Long, TransactionNode> TRANSACTION_MAP = new HashMap<>();
    private static final Map<String, List<Transaction>> TRANSACTION_TYPE_MAP = new HashMap<>();

    @Override
    public TransactionNode save(final Transaction transaction) throws TransactionAlreadyExistException {
        if (TRANSACTION_MAP.get(transaction.getId()) != null) {
            throw TransactionAlreadyExistException.create();
        }
        addToTransactionTypeMap(transaction);
        TransactionNode transactionNode = new TransactionNode(transaction);
        TRANSACTION_MAP.put(transaction.getId(), transactionNode);
        addToParents(transaction);
        return TRANSACTION_MAP.get(transaction.getId());
    }

    private void addToParents(final Transaction transaction) {
        if (transaction.getParentId() != null) {
            final TransactionNode transactionParentNode = TRANSACTION_MAP.get(transaction.getParentId());
            transactionParentNode.addTransaction(transaction);
        }
    }


    @Override
    public TransactionNode get(final Long transactionID) {
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
