package com.playground.solidjava.framework;

/**
 * A functional interface for executing step logic.
 * 
 * This allows steps to be defined inline as lambdas without the overhead of
 * creating a separate class. For more complex steps or to reduce duplication,
 * implement StepAction directly.
 * 
 * Example:
 * 
 * <pre>
 * given(ctx -> ctx.put("value", 42), "a value")
 * </pre>
 */
@FunctionalInterface
public interface Step {
    /**
     * Execute the step logic.
     * 
     * @param context Shared context for the scenario
     * @throws Exception if the step fails
     */
    void execute(StepContext context) throws Exception;
}
