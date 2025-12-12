package com.playground.solidjava.ocp;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Open/Closed Principle: This class extends Discount without modifying the interface.
 */
public class PercentageDiscount implements Discount {
    private final BigDecimal percentage;

    public PercentageDiscount(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(new BigDecimal(100)) > 0) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    @Override
    public BigDecimal apply(BigDecimal amount) {
        BigDecimal discount = amount.multiply(percentage).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        return amount.subtract(discount);
    }

    @Override
    public String getDescription() {
        return percentage + "% discount";
    }
}
