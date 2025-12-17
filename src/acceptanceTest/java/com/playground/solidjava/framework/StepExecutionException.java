package com.playground.solidjava.framework;

/**
 * Thrown when a step action fails to execute during an acceptance test.
 * 
 * Wraps the underlying exception with context about which step failed,
 * making test failures easier to diagnose and report.
 */
public class StepExecutionException extends RuntimeException {
    private final StepKeyword keyword;
    private final StepAction action;

    public StepExecutionException(StepKeyword keyword, StepAction action, Throwable cause) {
        super(formatMessage(keyword, action), cause);
        this.keyword = keyword;
        this.action = action;
    }

    private static String formatMessage(StepKeyword keyword, StepAction action) {
        return String.format("%s step failed: %s",
                keyword.getLabel(),
                action.description());
    }

    /**
     * Get the keyword of the step that failed.
     */
    public StepKeyword getKeyword() {
        return keyword;
    }

    /**
     * Get the action that failed.
     */
    public StepAction getAction() {
        return action;
    }
}
