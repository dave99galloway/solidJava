package com.playground.solidjava.srp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Single Responsibility Principle.
 * This test class has ONE reason to change: when SRP-related business logic changes.
 * It delegates specific assertions to helper methods with focused responsibilities.
 */
@DisplayName("Single Responsibility Principle Tests")
class PaymentProcessorTest {
    private PaymentProcessor processor;
    private PaymentValidator validator;
    private TestPaymentRecorder recorder;

    @BeforeEach
    void setUp() {
        validator = new PaymentValidator();
        recorder = new TestPaymentRecorder();
        processor = new PaymentProcessor(validator, recorder);
    }

    @Test
    @DisplayName("Should accept valid payment")
    void shouldProcessValidPayment() {
        // Arrange
        Payment payment = new Payment("TXN123", new BigDecimal("100.00"), "USD");

        // Act
        boolean result = processor.processPayment(payment);

        // Assert
        assertThat(result).isTrue();
        assertThat(recorder.successfulPayments()).containsExactly(payment);
    }

    @Test
    @DisplayName("Should reject payment with invalid amount")
    void shouldRejectInvalidAmount() {
        // Arrange
        Payment payment = new Payment("TXN124", new BigDecimal("0"), "USD");

        // Act
        boolean result = processor.processPayment(payment);

        // Assert
        assertThat(result).isFalse();
        assertThat(recorder.failedPayments()).isEmpty();
    }

    @Test
    @DisplayName("Should reject payment with invalid currency code")
    void shouldRejectInvalidCurrency() {
        // Arrange
        Payment payment = new Payment("TXN125", new BigDecimal("100.00"), "INVALID");

        // Act
        boolean result = processor.processPayment(payment);

        // Assert
        assertThat(result).isFalse();
        assertThat(recorder.successfulPayments()).isEmpty();
    }

    /**
     * Test helper: Encapsulates assertion logic for payment validation.
     * Single responsibility: validates that a payment meets amount criteria.
     */
    private void assertPaymentAmountValid(BigDecimal amount) {
        assertThat(amount).isGreaterThan(BigDecimal.ZERO).isLessThanOrEqualTo(new BigDecimal("999999.99"));
    }

    /**
     * Test helper: Segregates logging assertions from business logic assertions.
     * Single responsibility: validates recording behavior.
     */
    private void assertPaymentWasRecorded(Payment payment, boolean success) {
        if (success) {
            assertThat(recorder.successfulPayments()).contains(payment);
        } else {
            assertThat(recorder.failedPayments()).contains(payment);
        }
    }

    /**
     * Test helper: Tracks recorded payments.
     * Single responsibility: records test payment attempts.
     */
    static class TestPaymentRecorder extends PaymentRecorder {
        private final java.util.List<Payment> successfulPayments = new java.util.ArrayList<>();
        private final java.util.List<Payment> failedPayments = new java.util.ArrayList<>();

        @Override
        public void recordSuccessfulPayment(Payment payment) {
            super.recordSuccessfulPayment(payment);
            successfulPayments.add(payment);
        }

        @Override
        public void recordFailedPayment(Payment payment) {
            super.recordFailedPayment(payment);
            failedPayments.add(payment);
        }

        java.util.List<Payment> successfulPayments() {
            return new java.util.ArrayList<>(successfulPayments);
        }

        java.util.List<Payment> failedPayments() {
            return new java.util.ArrayList<>(failedPayments);
        }
    }
}
