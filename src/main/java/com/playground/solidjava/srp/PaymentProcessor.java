package com.playground.solidjava.srp;

/**
 * Single Responsibility Principle: This class has ONE reason to change - when payment processing logic changes.
 * It delegates other concerns to specialized classes.
 */
public class PaymentProcessor {
    private final PaymentValidator validator;
    private final PaymentRecorder recorder;

    public PaymentProcessor(PaymentValidator validator, PaymentRecorder recorder) {
        this.validator = validator;
        this.recorder = recorder;
    }

    public boolean processPayment(Payment payment) {
        if (!validator.isValid(payment)) {
            return false;
        }

        boolean success = executeTransaction(payment);

        if (success) {
            recorder.recordSuccessfulPayment(payment);
        } else {
            recorder.recordFailedPayment(payment);
        }

        return success;
    }

    private boolean executeTransaction(Payment payment) {
        // Simulated transaction
        return payment.getAmount().compareTo(java.math.BigDecimal.ZERO) > 0;
    }
}
