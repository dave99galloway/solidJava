package com.playground.solidjava.ocp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Open/Closed Principle: This calculator is CLOSED for modification but OPEN for extension.
 * New discount types can be added without changing this class.
 */
public class PriceCalculator {
    private final List<Discount> discounts = new ArrayList<>();

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public BigDecimal calculateFinalPrice(BigDecimal basePrice) {
        BigDecimal result = basePrice;
        for (Discount discount : discounts) {
            result = discount.apply(result);
        }
        return result;
    }

    public String getAppliedDiscounts() {
        return discounts.stream()
                .map(Discount::getDescription)
                .reduce((a, b) -> a + ", " + b)
                .orElse("No discounts");
    }
}
