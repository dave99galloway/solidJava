package com.playground.solidjava.srp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Single Responsibility Principle: This class has ONE reason to change - recording logic.
 */
public class PaymentRecorder {
    private static final Logger logger = LoggerFactory.getLogger(PaymentRecorder.class);

    public void recordSuccessfulPayment(Payment payment) {
        logger.info("Payment recorded: ID={}, Amount={} {}", 
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getCurrency());
    }

    public void recordFailedPayment(Payment payment) {
        logger.warn("Failed payment recorded: ID={}, Amount={} {}", 
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getCurrency());
    }
}
