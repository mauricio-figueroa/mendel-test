package com.mendel.transaction.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mendel.transaction.dto.TransactionAmountDTO;
import com.mendel.transaction.dto.TransactionRequestDTO;
import com.mendel.transaction.model.Transaction;
import com.mendel.transaction.service.TransactionService;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping(path = "transactions/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> createTransaction(@RequestBody final TransactionRequestDTO newTransaction, @PathVariable final Long id) {
        final Transaction transaction = transactionService.createTransaction(newTransaction, id);
        return ResponseEntity.ok().body(transaction);
    }

    @GetMapping(path = "transaction/sum/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionAmountDTO> getTransactionAmount(@PathVariable final Long id) {
        final TransactionAmountDTO transactionAmountById = transactionService.getTransactionAmountById(id);
        return ResponseEntity.ok().body(transactionAmountById);
    }

    @GetMapping(path = "transaction/types/{type}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> getTransactionByType(@PathVariable final String type) {
        List<Long> transactionIDs = transactionService.getTransactionsByType(type);
        return ResponseEntity.ok().body(transactionIDs);
    }
}
