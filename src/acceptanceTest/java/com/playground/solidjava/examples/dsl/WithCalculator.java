package com.playground.solidjava.examples.dsl;

import com.playground.solidjava.examples.Calculator;
import com.playground.solidjava.framework.StepAction;
import com.playground.solidjava.framework.StepContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Provides a fluent, statically-imported DSL for Calculator acceptance tests.
 * This class contains factory methods for all stateless, reusable steps.
 * The stateful calculator object is expected to be in the StepContext at creation.
 */
public final class WithCalculator {

    // Private constructor to prevent instantiation
    private WithCalculator() {
    }

    /**
     * WHEN: Enters a number into the calculator.
     */
    public static StepAction enterNumber(int number) {
        return new StepAction() {
            @Override
            public void execute(StepContext context) {
                context.get(Calculator.class, Calculator.class).enter(number);
            }

            @Override
            public String description() {
                return "enter number " + number;
            }
        };
    }

    /**
     * WHEN: Presses the add button. The result of the operation is stored in the
     * context as "result".
     */
    public static StepAction pressAdd() {
        return new StepAction() {
            @Override
            public void execute(StepContext context) {
                int result = context.get(Calculator.class, Calculator.class).add();
                context.put("result", result);
            }

            @Override
            public String description() {
                return "press add";
            }
        };
    }

    /**
     * WHEN: Presses the subtract button. The result of the operation is stored in
     * the context as "result".
     */
    public static StepAction pressSubtract() {
        return new StepAction() {
            @Override
            public void execute(StepContext context) {
                int result = context.get(Calculator.class, Calculator.class).subtract();
                context.put("result", result);
            }

            @Override
            public String description() {
                return "press subtract";
            }
        };
    }

    /**
     * WHEN: Resets the calculator's state.
     */
    public static StepAction pressReset() {
        return new StepAction() {
            @Override
            public void execute(StepContext context) {
                context.get(Calculator.class, Calculator.class).reset();
            }

            @Override
            public String description() {
                return "press reset";
            }
        };
    }

    /**
     * THEN: Asserts that the calculator's display shows the expected value.
     */
    public static StepAction displayShows(int expected) {
        return new StepAction() {
            @Override
            public void execute(StepContext context) {
                int actual = context.get(Calculator.class, Calculator.class).getDisplay();
                assertThat(actual).isEqualTo(expected);
            }

            @Override
            public String description() {
                return "display shows " + expected;
            }
        };
    }

    /**
     * THEN: Asserts that the result of the last operation is the expected value.
     */
    public static StepAction resultIs(int expected) {
        return new StepAction() {
            @Override
            public void execute(StepContext context) {
                int actual = context.get("result");
                assertThat(actual).isEqualTo(expected);
            }

            @Override
            public String description() {
                return "result is " + expected;
            }
        };
    }
}
