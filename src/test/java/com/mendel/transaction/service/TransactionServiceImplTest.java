package com.mendel.transaction.service;

import java.math.BigDecimal;
import java.util.List;

import com.mendel.transaction.dto.TransactionAmountDTO;
import com.mendel.transaction.dto.TransactionRequestDTO;
import com.mendel.transaction.exception.BusinessException;
import com.mendel.transaction.exception.TransactionAlreadyExistException;
import com.mendel.transaction.exception.TransactionNotFoundException;
import com.mendel.transaction.model.Transaction;
import com.mendel.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl target;

    @Test
    void createTransactionSuccess() throws BusinessException {
        final TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(null, BigDecimal.valueOf(122.5), "card");
        final Transaction transaction = new Transaction(1L, transactionRequestDTO.getParentId(), transactionRequestDTO.getAmount(), transactionRequestDTO.getType());

        Mockito.when(transactionRepository.save(any())).thenReturn(new TransactionNode(transaction));

        final Transaction transactionResponse = target.createTransaction(transactionRequestDTO, 1L);

        assertEquals(
                transaction,
                transactionResponse,
                "tx validator status doesn't match");
    }

    @Test
    void createTransactionError_txAlreadyExist() throws TransactionAlreadyExistException {
        final TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(null, BigDecimal.valueOf(122.5), "card");
        Mockito.when(transactionRepository.save(any())).thenThrow(TransactionAlreadyExistException.create());
        final TransactionAlreadyExistException transactionNotFoundException =
                assertThrows(TransactionAlreadyExistException.class, () -> target.createTransaction(transactionRequestDTO, 2L));

        assertEquals(
                SC_BAD_REQUEST,
                transactionNotFoundException.getStatusCode(),
                "The transaction you are trying to create already exists");
    }


    @Test
    void createTransactionError_parentIdNotExist() {
        final TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(1L, BigDecimal.valueOf(122.5), "card");
        Mockito.when(transactionRepository.get(transactionRequestDTO.getParentId())).thenReturn(null);

        final TransactionNotFoundException transactionNotFoundException =
                assertThrows(TransactionNotFoundException.class, () -> target.createTransaction(transactionRequestDTO, 2L));

        assertEquals(
                SC_NOT_FOUND,
                transactionNotFoundException.getStatusCode(),
                "Transaction doesn't exist");
    }


    @Test
    void getTransactionAmountById() throws BusinessException {

        Transaction transaction1 = new Transaction(1L, null, BigDecimal.valueOf(150.5), "cards");
        Transaction transaction2 = new Transaction(2L, 1L, BigDecimal.valueOf(149.5), "cards");

        TransactionNode transactionNode1 = new TransactionNode(transaction1);
        transactionNode1.addTransaction(transaction2);
        TransactionNode transactionNode2 = new TransactionNode(transaction2);
        Mockito.when(transactionRepository.get(transaction1.getId())).thenReturn(transactionNode1);
        Mockito.when(transactionRepository.get(transaction2.getId())).thenReturn(transactionNode2);


        final TransactionAmountDTO transactionAmountById = target.getTransactionAmountById(1L);

        assertEquals(
                BigDecimal.valueOf(300.0),
                transactionAmountById.getSum(),
                "value doesn't match");
    }

    @Test
    void getTransactionAmountByIdError_NotFound() {

        Transaction transaction1 = new Transaction(1L, null, BigDecimal.valueOf(150.5), "cards");
        Transaction transaction2 = new Transaction(2L, 1L, BigDecimal.valueOf(149.5), "cards");

        TransactionNode transactionNode1 = new TransactionNode(transaction1);
        transactionNode1.addTransaction(transaction2);
        Mockito.when(transactionRepository.get(transaction1.getId())).thenReturn(transactionNode1);
        Mockito.when(transactionRepository.get(transaction2.getId())).thenReturn(null);

        final TransactionNotFoundException transactionNotFoundException =
                assertThrows(TransactionNotFoundException.class, () -> target.getTransactionAmountById(1L));

        assertEquals(
                SC_NOT_FOUND,
                transactionNotFoundException.getStatusCode(),
                "Transaction doesn't exist");
    }


    @Test
    void getTransactionsByType() {
        Transaction transaction1 = new Transaction(1L, null, BigDecimal.valueOf(150.5), "cards");

        Mockito.when(transactionRepository.getTransactionsByType(transaction1.getType())).thenReturn(List.of(transaction1));

        final List<Long> transaction = target.getTransactionsByType(transaction1.getType());

        assertEquals(
                transaction,
                List.of(1L),
                "transaction id list doesn't match");
    }

}
