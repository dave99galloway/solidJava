package com.playground.solidjava.lsp;

import java.math.BigDecimal;

/**
 * Liskov Substitution Principle: Any subclass must be substitutable for its parent.
 * The contract defined here must be honored by all implementations.
 */
public abstract class Shape {
    /**
     * Calculate the area of the shape.
     * This contract must be correctly implemented by all subclasses.
     */
    public abstract BigDecimal getArea();

    /**
     * Get the perimeter of the shape.
     * This contract must be correctly implemented by all subclasses.
     */
    public abstract BigDecimal getPerimeter();

    public final boolean isValid() {
        return getArea().compareTo(BigDecimal.ZERO) > 0
                && getPerimeter().compareTo(BigDecimal.ZERO) > 0;
    }
}
