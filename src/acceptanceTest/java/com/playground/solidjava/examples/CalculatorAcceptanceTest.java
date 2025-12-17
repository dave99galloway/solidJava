package com.playground.solidjava.examples;

import com.playground.solidjava.framework.AcceptanceTest;
import com.playground.solidjava.framework.StepCommand;
import com.playground.solidjava.framework.StepContext;
import com.playground.solidjava.framework.StepKeyword;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.*;

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

    @Test
    void shouldAddTwoPositiveNumbers() {
        given(new GivenCalculator())
                .and(new GivenEnterNumber(5))
                .and(new GivenEnterNumber(3))
                .when(new WhenAdd())
                .then(new ThenResultIs(8));
    }

    @Test
    void shouldSubtractNumbers() {
        given(new GivenCalculator())
                .and(new GivenEnterNumber(10))
                .and(new GivenEnterNumber(4))
                .when(new WhenSubtract())
                .then(new ThenResultIs(6));
    }

    @Test
    void shouldHandleZero() {
        given(new GivenCalculator())
                .when(new WhenReset())
                .then(new ThenDisplayShows(0));
    }

    // Command implementations

    private class GivenCalculator implements StepCommand {
        @Override
        public StepKeyword keyword() {
            return StepKeyword.GIVEN;
        }

        @Override
        public void execute(StepContext context) {
            calculator = new Calculator();
            context.put("calculator", calculator);
            LoggerFactory.getLogger(getClass()).debug("  └─ Calculator.create()");
        }

        @Override
        public String description() {
            return "a calculator";
        }
    }

    private class GivenEnterNumber implements StepCommand {
        private final int number;

        GivenEnterNumber(int number) {
            this.number = number;
        }

        @Override
        public StepKeyword keyword() {
            return StepKeyword.AND;
        }

        @Override
        public void execute(StepContext context) {
            calculator.enter(number);
            context.put("lastNumber", number);
            var logger = LoggerFactory.getLogger(getClass());
            logger.debug("  └─ Calculator.enter({})", number);
            logger.debug("  → Number stored: {}", number);
        }

        @Override
        public String description() {
            return "I enter " + number;
        }
    }

    private class WhenAdd implements StepCommand {
        @Override
        public StepKeyword keyword() {
            return StepKeyword.WHEN;
        }

        @Override
        public void execute(StepContext context) {
            int result = calculator.add();
            context.put("result", result);
            var logger = LoggerFactory.getLogger(getClass());
            logger.debug("  └─ Calculator.add()");
            logger.debug("  → Calculation executed, result = {}", result);
        }

        @Override
        public String description() {
            return "I press the add button";
        }
    }

    private class WhenSubtract implements StepCommand {
        @Override
        public StepKeyword keyword() {
            return StepKeyword.WHEN;
        }

        @Override
        public void execute(StepContext context) {
            int result = calculator.subtract();
            context.put("result", result);
            var logger = LoggerFactory.getLogger(getClass());
            logger.debug("  └─ Calculator.subtract()");
            logger.debug("  → Calculation executed, result = {}", result);
        }

        @Override
        public String description() {
            return "I press the subtract button";
        }
    }

    private class WhenReset implements StepCommand {
        @Override
        public StepKeyword keyword() {
            return StepKeyword.WHEN;
        }

        @Override
        public void execute(StepContext context) {
            calculator.reset();
            var logger = LoggerFactory.getLogger(getClass());
            logger.debug("  └─ Calculator.reset()");
            logger.debug("  → Calculator reset to initial state");
        }

        @Override
        public String description() {
            return "I start fresh";
        }
    }

    private class ThenResultIs implements StepCommand {
        private final int expected;

        ThenResultIs(int expected) {
            this.expected = expected;
        }

        @Override
        public StepKeyword keyword() {
            return StepKeyword.THEN;
        }

        @Override
        public void execute(StepContext context) {
            int actual = context.get("result");
            var logger = LoggerFactory.getLogger(getClass());
            logger.debug("  └─ Calculator.getResult()");
            logger.debug("  ✓ Asserting result = {}", expected);
            assertThat(actual)
                    .as("Calculator result should be correct")
                    .isEqualTo(expected);
        }

        @Override
        public String description() {
            return "the result should be " + expected;
        }
    }

    private class ThenDisplayShows implements StepCommand {
        private final int expected;

        ThenDisplayShows(int expected) {
            this.expected = expected;
        }

        @Override
        public StepKeyword keyword() {
            return StepKeyword.THEN;
        }

        @Override
        public void execute(StepContext context) {
            int display = calculator.getDisplay();
            var logger = LoggerFactory.getLogger(getClass());
            logger.debug("  └─ Calculator.getDisplay()");
            logger.debug("  ✓ Asserting display = {}", expected);
            assertThat(display)
                    .as("Display should show zero after reset")
                    .isEqualTo(expected);
        }

        @Override
        public String description() {
            return "the display should show " + expected;
        }
    }
}
