package com.playground.solidjava.lsp;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Liskov Substitution Principle: Circle correctly implements the Shape contract.
 * It can be used wherever Shape is expected.
 */
public class Circle extends Shape {
    private final BigDecimal radius;
    private static final BigDecimal PI = new BigDecimal("3.14159265358979323846");

    public Circle(BigDecimal radius) {
        if (radius.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
    }

    @Override
    public BigDecimal getArea() {
        return PI.multiply(radius).multiply(radius).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getPerimeter() {
        return PI.multiply(radius).multiply(new BigDecimal(2)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getRadius() {
        return radius;
    }
}
