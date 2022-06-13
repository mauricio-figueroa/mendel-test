package com.mendel.transaction.dto;

import java.math.BigDecimal;

public class TransactionAmountDTO {

    private final BigDecimal sum;

    public TransactionAmountDTO(final BigDecimal amount) {
        this.sum = amount;
    }

    public BigDecimal getSum() {
        return sum;
    }
}
