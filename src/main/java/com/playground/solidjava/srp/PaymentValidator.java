package com.playground.solidjava.srp;

import java.math.BigDecimal;

/**
 * Single Responsibility Principle: This class has ONE reason to change - validation logic.
 */
public class PaymentValidator {
    private static final BigDecimal MIN_AMOUNT = BigDecimal.ZERO;
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("999999.99");

    public boolean isValid(Payment payment) {
        return payment != null
                && isValidAmount(payment.getAmount())
                && isValidCurrency(payment.getCurrency());
    }

    private boolean isValidAmount(BigDecimal amount) {
        return amount != null
                && amount.compareTo(MIN_AMOUNT) > 0
                && amount.compareTo(MAX_AMOUNT) <= 0;
    }

    private boolean isValidCurrency(String currency) {
        return currency != null && !currency.isBlank() && currency.length() == 3;
    }
}
