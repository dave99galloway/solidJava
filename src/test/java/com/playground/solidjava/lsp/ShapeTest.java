package com.playground.solidjava.lsp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Liskov Substitution Principle.
 * This test demonstrates that any Shape subclass can be used wherever Shape is expected,
 * and they all correctly fulfill the Shape contract.
 */
@DisplayName("Liskov Substitution Principle Tests")
class ShapeTest {

    @Test
    @DisplayName("Rectangle should correctly implement Shape contract")
    void rectangleShouldImplementShapeContract() {
        // Arrange
        Rectangle rectangle = new Rectangle(new BigDecimal("5"), new BigDecimal("3"));

        // Act & Assert
        assertThatRectangleIsValid(rectangle);
    }

    @Test
    @DisplayName("Circle should correctly implement Shape contract")
    void circleShouldImplementShapeContract() {
        // Arrange
        Circle circle = new Circle(new BigDecimal("5"));

        // Act & Assert
        assertThatCircleIsValid(circle);
    }

    @Test
    @DisplayName("All shape types can be used interchangeably")
    void allShapesShouldBeSubstitutable() {
        // Arrange
        List<Shape> shapes = Arrays.asList(
                new Rectangle(new BigDecimal("4"), new BigDecimal("6")),
                new Circle(new BigDecimal("5"))
        );

        // Act & Assert - all shapes can be used in the same way
        assertThat(shapes)
                .allSatisfy(shape -> assertThat(shape.isValid()).isTrue())
                .allSatisfy(shape -> assertThat(shape.getArea()).isGreaterThan(BigDecimal.ZERO))
                .allSatisfy(shape -> assertThat(shape.getPerimeter()).isGreaterThan(BigDecimal.ZERO));
    }

    @Test
    @DisplayName("Rectangle area calculation should be correct")
    void rectangleAreaShouldBeCorrect() {
        // Arrange
        Rectangle rectangle = new Rectangle(new BigDecimal("5"), new BigDecimal("3"));

        // Act
        BigDecimal area = rectangle.getArea();

        // Assert
        assertThat(area).isEqualByComparingTo(new BigDecimal("15"));
    }

    @Test
    @DisplayName("Rectangle perimeter calculation should be correct")
    void rectanglePerimeterShouldBeCorrect() {
        // Arrange
        Rectangle rectangle = new Rectangle(new BigDecimal("5"), new BigDecimal("3"));

        // Act
        BigDecimal perimeter = rectangle.getPerimeter();

        // Assert
        assertThat(perimeter).isEqualByComparingTo(new BigDecimal("16"));
    }

    @Test
    @DisplayName("Circle area calculation should be correct")
    void circleAreaShouldBeCorrect() {
        // Arrange
        Circle circle = new Circle(new BigDecimal("5"));

        // Act
        BigDecimal area = circle.getArea();

        // Assert
        // Area = π * r² ≈ 3.14 * 25 ≈ 78.54
        assertThat(area).isGreaterThan(new BigDecimal("78")).isLessThan(new BigDecimal("79"));
    }

    @Test
    @DisplayName("Circle perimeter calculation should be correct")
    void circlePerimeterShouldBeCorrect() {
        // Arrange
        Circle circle = new Circle(new BigDecimal("5"));

        // Act
        BigDecimal perimeter = circle.getPerimeter();

        // Assert
        // Perimeter = 2 * π * r ≈ 2 * 3.14 * 5 ≈ 31.42
        assertThat(perimeter).isGreaterThan(new BigDecimal("31")).isLessThan(new BigDecimal("32"));
    }

    @Test
    @DisplayName("Invalid dimensions should throw exception")
    void invalidDimensionsShouldThrowException() {
        // Assert
        assertThatThrownBy(() -> new Rectangle(BigDecimal.ZERO, new BigDecimal("5")))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Circle(new BigDecimal("-5")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test helper: Validates that a rectangle correctly implements the Shape contract.
     * Liskov Substitution: ensures the contract is properly honored.
     */
    private void assertThatRectangleIsValid(Rectangle rectangle) {
        assertThat(rectangle.isValid()).isTrue();
        assertThat(rectangle.getArea())
                .isEqualByComparingTo(rectangle.getWidth().multiply(rectangle.getHeight()));
        assertThat(rectangle.getPerimeter())
                .isEqualByComparingTo(
                        rectangle.getWidth().add(rectangle.getHeight()).multiply(new BigDecimal(2))
                );
    }

    /**
     * Test helper: Validates that a circle correctly implements the Shape contract.
     * Liskov Substitution: ensures the contract is properly honored.
     */
    private void assertThatCircleIsValid(Circle circle) {
        assertThat(circle.isValid()).isTrue();
        assertThat(circle.getArea()).isGreaterThan(BigDecimal.ZERO);
        assertThat(circle.getPerimeter()).isGreaterThan(BigDecimal.ZERO);
    }
}
