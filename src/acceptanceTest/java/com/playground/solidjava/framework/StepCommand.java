package com.playground.solidjava.framework;

/**
 * A step command encapsulates a test step (Given/When/Then/And/But) with its
 * keyword.
 * 
 * This interface is typically used internally by the framework or when you need
 * to decouple the keyword from the execution context. Most tests should
 * implement
 * StepAction instead.
 * 
 * Each command is responsible for:
 * 1. Declaring which step keyword it belongs to
 * 2. Executing the step logic
 * 3. Storing/retrieving state via StepContext
 * 
 * This pattern avoids string parsing and provides explicit, testable behavior
 * for each step of an acceptance test scenario.
 */
public interface StepCommand extends StepAction {
    /**
     * The step keyword this command belongs to (GIVEN, WHEN, THEN, AND, BUT).
     */
    StepKeyword keyword();
}
