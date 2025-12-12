package com.playground.solidjava.lsp;

import java.math.BigDecimal;

/**
 * Liskov Substitution Principle: Rectangle correctly implements the Shape contract.
 * It can be used wherever Shape is expected.
 */
public class Rectangle extends Shape {
    private final BigDecimal width;
    private final BigDecimal height;

    public Rectangle(BigDecimal width, BigDecimal height) {
        if (width.compareTo(BigDecimal.ZERO) <= 0 || height.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public BigDecimal getArea() {
        return width.multiply(height);
    }

    @Override
    public BigDecimal getPerimeter() {
        return width.add(height).multiply(new BigDecimal(2));
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getHeight() {
        return height;
    }
}
