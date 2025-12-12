package com.playground.solidjava.ocp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Open/Closed Principle.
 * This test class demonstrates that new discount types can be added without modifying this test.
 * It uses polymorphism to test multiple discount implementations with the same test logic.
 */
@DisplayName("Open/Closed Principle Tests")
class PriceCalculatorTest {
    private PriceCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new PriceCalculator();
    }

    @Test
    @DisplayName("Should apply percentage discount correctly")
    void shouldApplyPercentageDiscount() {
        // Arrange
        calculator.addDiscount(new PercentageDiscount(new BigDecimal("10")));

        // Act
        BigDecimal result = calculator.calculateFinalPrice(new BigDecimal("100.00"));

        // Assert
        assertThat(result).isEqualByComparingTo(new BigDecimal("90.00"));
    }

    @Test
    @DisplayName("Should apply fixed discount correctly")
    void shouldApplyFixedDiscount() {
        // Arrange
        calculator.addDiscount(new FixedDiscount(new BigDecimal("15.00")));

        // Act
        BigDecimal result = calculator.calculateFinalPrice(new BigDecimal("100.00"));

        // Assert
        assertThat(result).isEqualByComparingTo(new BigDecimal("85.00"));
    }

    @Test
    @DisplayName("Should apply multiple discounts in sequence")
    void shouldApplyMultipleDiscounts() {
        // Arrange
        calculator.addDiscount(new PercentageDiscount(new BigDecimal("10")));
        calculator.addDiscount(new FixedDiscount(new BigDecimal("5.00")));

        // Act
        BigDecimal result = calculator.calculateFinalPrice(new BigDecimal("100.00"));

        // Assert
        // First: 100 * 0.9 = 90
        // Then: 90 - 5 = 85
        assertThat(result).isEqualByComparingTo(new BigDecimal("85.00"));
    }

    @Test
    @DisplayName("Should prevent negative final price with fixed discount")
    void shouldNotAllowNegativePriceWithFixedDiscount() {
        // Arrange
        calculator.addDiscount(new FixedDiscount(new BigDecimal("150.00")));

        // Act
        BigDecimal result = calculator.calculateFinalPrice(new BigDecimal("100.00"));

        // Assert
        assertThat(result).isZero();
    }

    @ParameterizedTest
    @CsvSource({
            "100.00, 10, 90.00",
            "200.00, 25, 150.00",
            "50.00, 50, 25.00"
    })
    @DisplayName("Should calculate percentage discounts correctly")
    void shouldCalculatePercentageDiscountsCorrectly(BigDecimal basePrice, BigDecimal percentage, BigDecimal expected) {
        // Arrange
        Discount discount = new PercentageDiscount(percentage);
        calculator.addDiscount(discount);

        // Act
        BigDecimal result = calculator.calculateFinalPrice(basePrice);

        // Assert
        assertThat(result).isEqualByComparingTo(expected);
    }

    @Test
    @DisplayName("Should describe applied discounts")
    void shouldDescribeAppliedDiscounts() {
        // Arrange
        calculator.addDiscount(new PercentageDiscount(new BigDecimal("10")));
        calculator.addDiscount(new FixedDiscount(new BigDecimal("5.00")));

        // Act
        String description = calculator.getAppliedDiscounts();

        // Assert
        assertThat(description)
                .contains("10% discount")
                .contains("$5.00 fixed discount");
    }

    @Test
    @DisplayName("Should handle no discounts")
    void shouldHandleNoDiscounts() {
        // Arrange - no discounts added

        // Act
        BigDecimal result = calculator.calculateFinalPrice(new BigDecimal("100.00"));

        // Assert
        assertThat(result).isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(calculator.getAppliedDiscounts()).isEqualTo("No discounts");
    }

    /**
     * Test helper: Validates that a discount implementation honors its contract.
     * This demonstrates Open/Closed principle in testing: new discount types
     * can be tested using this same validation without modifying it.
     */
    private void assertDiscountIsValid(Discount discount, BigDecimal input, BigDecimal expectedOutput) {
        assertThat(discount.apply(input)).isEqualByComparingTo(expectedOutput);
        assertThat(discount.getDescription()).isNotBlank();
    }
}
