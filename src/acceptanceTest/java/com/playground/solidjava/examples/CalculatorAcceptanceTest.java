package com.playground.solidjava.examples;

import com.playground.solidjava.framework.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.playground.solidjava.examples.dsl.WithCalculator.*;

/**
 * Example acceptance test demonstrating the fluent DSL framework with Command
 * pattern.
 *
 * This test shows:
 * - Fluent Given/When/Then/And chaining with commands
 * - Each command encapsulates behavior and logging
 * - Commands have access to StepContext for state sharing
 * - Direct use of AssertJ for assertions
 * - No string parsing or extraction
 */
public class CalculatorAcceptanceTest extends AcceptanceTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        this.calculator = new Calculator();
    }

    @Test
    void shouldAddTwoPositiveNumbers() {
        given(calculator, "a calculator")
                .and(ctx -> calculator.enter(5), "I enter 5")
                .and(enterNumber(3))
                .when(pressAdd())
                .then(resultIs(8));
    }

    @Test
    void shouldSubtractNumbers() {
        given(calculator, "a calculator")
                .and(enterNumber(10))
                .and(enterNumber(4))
                .when(pressSubtract())
                .then(resultIs(6));
    }

    @Test
    void shouldHandleZero() {
        given(calculator, "a calculator")
                .when(pressReset())
                .then(displayShows(0));
    }
}
