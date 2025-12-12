package com.playground.solidjava.cucumber;

import com.playground.solidjava.srp.Payment;
import com.playground.solidjava.srp.PaymentProcessor;
import com.playground.solidjava.srp.PaymentRecorder;
import com.playground.solidjava.srp.PaymentValidator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Cucumber step definitions for Single Responsibility Principle.
 * This class demonstrates SRP in testing: it has ONE reason to change - when SRP scenarios change.
 * It delegates specific concerns to helper methods.
 */
public class SRPSteps {
    private PaymentProcessor processor;
    private PaymentValidator validator;
    private TestRecorder recorder;
    private Payment payment;
    private boolean validationResult;
    private boolean processingResult;

    @Given("a payment processor is initialized")
    public void initializePaymentProcessor() {
        validator = new PaymentValidator();
        recorder = new TestRecorder();
        processor = new PaymentProcessor(validator, recorder);
    }

    @Given("a payment validator")
    public void initializePaymentValidator() {
        validator = new PaymentValidator();
    }

    @When("I process a payment with amount {word} and currency {word}")
    public void processPayment(String amount, String currency) {
        payment = new Payment("TXN" + System.currentTimeMillis(), new BigDecimal(amount), currency);
        processingResult = processor.processPayment(payment);
    }

    @When("I validate a payment with amount {word} and currency {word}")
    public void validatePayment(String amount, String currency) {
        payment = new Payment("TXN" + System.currentTimeMillis(), new BigDecimal(amount), currency);
        validationResult = validator.isValid(payment);
    }

    @Then("the payment should be accepted")
    public void assertPaymentAccepted() {
        assertThat(processingResult).isTrue();
    }

    @Then("the payment should be recorded")
    public void assertPaymentRecorded() {
        assertThat(recorder.getRecordedPayments()).contains(payment);
    }

    @Then("the validation should fail")
    public void assertValidationFailed() {
        assertThat(validationResult).isFalse();
    }

    /**
     * Test helper: Simple recorder for Cucumber tests.
     * Single Responsibility: only records payments.
     */
    static class TestRecorder extends PaymentRecorder {
        private final java.util.List<Payment> recordedPayments = new java.util.ArrayList<>();

        @Override
        public void recordSuccessfulPayment(Payment payment) {
            super.recordSuccessfulPayment(payment);
            recordedPayments.add(payment);
        }

        java.util.List<Payment> getRecordedPayments() {
            return new java.util.ArrayList<>(recordedPayments);
        }
    }
}
