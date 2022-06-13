package com.mendel.transaction.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendel.transaction.dto.TransactionAmountDTO;
import com.mendel.transaction.dto.TransactionRequestDTO;
import com.mendel.transaction.exception.BusinessException;
import com.mendel.transaction.exception.TransactionNotFoundException;
import com.mendel.transaction.model.Transaction;
import com.mendel.transaction.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(final TransactionRequestDTO transactionRequestDTO, final Long id) throws BusinessException {
        validateNewTransaction(transactionRequestDTO);
        Transaction transaction = new Transaction(id, transactionRequestDTO.getParentId(), transactionRequestDTO.getAmount(), transactionRequestDTO.getType());
        return transactionRepository.save(transaction);
    }

    @Override
    public TransactionAmountDTO getTransactionAmountById(final Long id) throws TransactionNotFoundException {
        final BigDecimal totalAmount = getTotalAmount(id);
        return new TransactionAmountDTO(totalAmount);
    }

    @Override
    public List<Long> getTransactionsByType(final String type) {
        final List<Transaction> transactions = transactionRepository.getTransactionsByType(type);
        return transactions.stream().map(Transaction::getId).collect(Collectors.toList());
    }

    private void validateNewTransaction(final TransactionRequestDTO transactionRequestDTO) throws TransactionNotFoundException {
        if (transactionRequestDTO.getParentId() != null) {
            final Transaction transaction = transactionRepository.get(transactionRequestDTO.getParentId());
            if (transaction == null) {
                throw TransactionNotFoundException.create();
            }
        }
    }


    private BigDecimal getTotalAmount(final Long id) throws TransactionNotFoundException {
        final Transaction transaction = transactionRepository.get(id);

        if (transaction == null) {
            throw TransactionNotFoundException.create();
        }

        if (transaction.getParentId() == null) {
            return transaction.getAmount();
        } else {
            return transaction.getAmount().add(getTotalAmount(transaction.getParentId()));
        }

    }
}
