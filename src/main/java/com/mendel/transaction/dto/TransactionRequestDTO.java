package com.mendel.transaction.dto;

import java.math.BigDecimal;

public class TransactionRequestDTO {

    private final Long parentId;
    private final BigDecimal amount;
    private final String type;

    public TransactionRequestDTO(final Long parentId, final BigDecimal amount, final String type) {
        this.parentId = parentId;
        this.amount = amount;
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
}
