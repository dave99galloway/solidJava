package com.playground.solidjava.ocp;

import java.math.BigDecimal;

/**
 * Open/Closed Principle: Another implementation without modifying the interface or existing classes.
 */
public class FixedDiscount implements Discount {
    private final BigDecimal amount;

    public FixedDiscount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Discount amount cannot be negative");
        }
        this.amount = amount;
    }

    @Override
    public BigDecimal apply(BigDecimal price) {
        BigDecimal result = price.subtract(amount);
        return result.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : result;
    }

    @Override
    public String getDescription() {
        return "$" + amount + " fixed discount";
    }
}
