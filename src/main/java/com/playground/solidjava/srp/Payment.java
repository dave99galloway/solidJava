package com.playground.solidjava.srp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private final String transactionId;
    private final BigDecimal amount;
    private final String currency;
    private final LocalDateTime timestamp;

    public Payment(String transactionId, BigDecimal amount, String currency) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
