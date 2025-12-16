package com.playground.solidjava.framework;

/**
 * A step command encapsulates a test step (Given/When/Then/And/But).
 * 
 * Each command is responsible for:
 * 1. Declaring which step keyword it belongs to
 * 2. Executing the step logic
 * 3. Storing/retrieving state via StepContext
 * 
 * This pattern avoids string parsing and provides explicit, testable behavior
 * for each step of an acceptance test scenario.
 */
public interface StepCommand {

    /**
     * The step keyword this command belongs to (GIVEN, WHEN, THEN, AND, BUT).
     */
    StepKeyword keyword();

    /**
     * Execute the step logic.
     * The command has full access to the test's StepContext for state management.
     * 
     * @param context Shared context for the scenario
     * @throws Exception if the step fails
     */
    void execute(StepContext context) throws Exception;

    /**
     * Provide a human-readable description of this step.
     * This is logged at INFO level by the framework along with the keyword.
     * 
     * @return description like "a calculator" or "I press add"
     */
    String description();
}
