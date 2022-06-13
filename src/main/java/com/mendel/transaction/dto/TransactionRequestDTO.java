package com.mendel.transaction.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionRequestDTO {

    @JsonProperty("parent_id")
    private final Long parentId;

    @NotNull
    @NumberFormat
    private final BigDecimal amount;
    @NotBlank
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
