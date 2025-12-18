package com.playground.solidjava.examples;

import com.playground.solidjava.framework.AcceptanceTest;
import com.playground.solidjava.framework.StepAction;
import com.playground.solidjava.framework.StepContext;
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
        given(new CreateCalculator())
                .and(ctx -> calculator.enter(5), "I enter 5")
                .and(new EnterNumber(3))
                .when(new Add())
                .then(new ResultIs(8));
    }

    @Test
    void shouldSubtractNumbers() {
        given(new CreateCalculator())
                .and(new EnterNumber(10))
                .and(new EnterNumber(4))
                .when(new Subtract())
                .then(new ResultIs(6));
    }

    @Test
    void shouldHandleZero() {
        given(new CreateCalculator())
                .when(new Reset())
                .then(new DisplayShows(0));
    }

    // Command implementations

    private class CreateCalculator implements StepAction {
        @Override
        public void execute(StepContext context) {
            calculator = new Calculator();
            context.put(calculator);
            LoggerFactory.getLogger(getClass()).debug("  └─ Calculator.create()");
        }

        @Override
        public String description() {
            return "a calculator";
        }
    }

    record EnterNumber(int number) implements StepAction {

        @Override
        public void execute(StepContext context) {
            context.get(Calculator.class, Calculator.class).enter(number);
            context.put("lastNumber", number);
        }

        @Override
        public String description() {
            return "I enter " + number;
        }
    }

    private class Add implements StepAction {
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

    private class Subtract implements StepAction {
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

    private class Reset implements StepAction {
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

    private class ResultIs implements StepAction {
        private final int expected;

        ResultIs(int expected) {
            this.expected = expected;
        }

        @Override
        public void execute(StepContext context) {
            int actual = context.get("result");
            var logger = LoggerFactory.getLogger(getClass());
            logger.debug("  └─ Calculator.getResult()");
            logger.debug("  ✓ Asserting result = {}", expected);
            assertThat(actual).isEqualTo(expected);
        }

        @Override
        public String description() {
            return "the result should be " + expected;
        }
    }

    private class DisplayShows implements StepAction {
        private final int expected;

        DisplayShows(int expected) {
            this.expected = expected;
        }

        @Override
        public void execute(StepContext context) {
            int display = calculator.getDisplay();
            var logger = LoggerFactory.getLogger(getClass());
            logger.debug("  └─ Calculator.getDisplay()");
            logger.debug("  ✓ Asserting display = {}", expected);
            assertThat(display).isEqualTo(expected);
        }

        @Override
        public String description() {
            return "the display should show " + expected;
        }
    }
}
