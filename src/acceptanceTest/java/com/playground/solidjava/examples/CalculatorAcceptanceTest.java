package com.playground.solidjava.examples;

import com.playground.solidjava.framework.AcceptanceTest;
import com.playground.solidjava.framework.StepAction;
import com.playground.solidjava.framework.StepContext;
import org.junit.jupiter.api.Test;

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
        }

        @Override
        public String description() {
            return "I enter " + number;
        }
    }

    private class Add implements StepAction {
        @Override
        public void execute(StepContext context) {
            context.put("result", calculator.add());
        }

        @Override
        public String description() {
            return "I press the add button";
        }
    }

    private class Subtract implements StepAction {
        @Override
        public void execute(StepContext context) {
            context.put("result", calculator.subtract());
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
        }

        @Override
        public String description() {
            return "I start fresh";
        }
    }

    record ResultIs(int expected) implements StepAction {
        @Override
        public void execute(StepContext context) {
            int actual = context.get("result");
            assertThat(actual).isEqualTo(expected);
        }

        @Override
        public String description() {
            return "the result should be " + expected;
        }
    }

    record DisplayShows(int expected) implements StepAction {
        @Override
        public void execute(StepContext context) {
            Calculator calculator = context.get(Calculator.class); 
            assertThat(calculator.getDisplay()).isEqualTo(expected);
        }

        @Override
        public String description() {
            return "the display should show " + expected;
        }
    }
}
