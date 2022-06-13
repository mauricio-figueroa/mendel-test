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
        return transactionRepository.save(transaction).getCurrentTransaction();
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
            final TransactionNode transactionNode = transactionRepository.get(transactionRequestDTO.getParentId());
            if (transactionNode == null) {
                throw TransactionNotFoundException.create();
            }
        }
    }


    private BigDecimal getTotalAmount(final Long id) throws TransactionNotFoundException {
        final TransactionNode transactionNode = transactionRepository.get(id);

        if (transactionNode == null) {
            throw TransactionNotFoundException.create();
        }
        final BigDecimal currentAmount = transactionNode.getCurrentTransaction().getAmount();

        if (transactionNode.getTransactionList().isEmpty()) {
            return currentAmount;
        } else {
            BigDecimal sum = BigDecimal.ZERO;
            for (final Long childID : transactionNode.getTransactionList()) {
                sum = sum.add(getTotalAmount(childID));
            }
            return currentAmount.add(sum);

        }

    }
}
