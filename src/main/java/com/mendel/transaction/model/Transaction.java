package com.mendel.transaction.model;

import java.math.BigDecimal;

public class Transaction {

    private final long id;
    private final long parentId;
    private final BigDecimal amount;

    public Transaction(final long id, final long parentId, final BigDecimal amount) {
        this.id = id;
        this.parentId = parentId;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public long getParentId() {
        return parentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
