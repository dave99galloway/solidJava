package com.playground.solidjava.framework;

/**
 * A step action encapsulates the core behavior of a test step.
 * 
 * Implementations are keyword-agnostic; the keyword is determined by which
 * method (given, when, then, and, but) the action is passed to.
 */
public interface StepAction {
    /**
     * Execute the step logic.
     * The action has full access to the test's StepContext for state management.
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
