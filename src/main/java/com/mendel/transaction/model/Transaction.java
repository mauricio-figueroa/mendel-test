package com.mendel.transaction.model;

import java.math.BigDecimal;

public class Transaction {

    private final Long id;
    private final Long parentId;
    private final BigDecimal amount;

    private final String type;

    public Transaction(final Long id, final Long parentId, final BigDecimal amount, final String type) {
        this.id = id;
        this.parentId = parentId;
        this.amount = amount;
        this.type = type;
    }

    public Long getId() {
        return id;
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
