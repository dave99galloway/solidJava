package com.playground.solidjava.ocp;

import java.math.BigDecimal;

/**
 * Open/Closed Principle: This interface is OPEN for extension but CLOSED for modification.
 * New discount types can be added without modifying existing code.
 */
public interface Discount {
    BigDecimal apply(BigDecimal amount);

    String getDescription();
}
