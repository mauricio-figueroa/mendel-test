package com.mendel.transaction.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mendel.transaction.dto.TransactionAmountDTO;
import com.mendel.transaction.dto.TransactionRequestDTO;
import com.mendel.transaction.exception.BusinessException;
import com.mendel.transaction.exception.TransactionNotFoundException;
import com.mendel.transaction.model.Transaction;
import com.mendel.transaction.service.TransactionService;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Valid
    @PutMapping(path = "transactions/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody final TransactionRequestDTO newTransaction, @NotBlank(message = "id can't be blank") @PathVariable final Long id) throws BusinessException {
        final Transaction transaction = transactionService.createTransaction(newTransaction, id);
        return ResponseEntity.ok().body(transaction);
    }

    @Valid
    @GetMapping(path = "transaction/sum/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionAmountDTO> getTransactionAmount(@NotBlank(message = "id can't be blank") @PathVariable final Long id) throws TransactionNotFoundException {
        final TransactionAmountDTO transactionAmountById = transactionService.getTransactionAmountById(id);
        return ResponseEntity.ok().body(transactionAmountById);
    }

    @Valid
    @GetMapping(path = "transaction/types/{type}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> getTransactionByType(@NotBlank(message = "type can't be blank") @PathVariable final String type) {
        List<Long> transactionIDs = transactionService.getTransactionsByType(type);
        return ResponseEntity.ok().body(transactionIDs);
    }
}
