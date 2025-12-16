package com.playground.solidjava.framework;

/**
 * Thrown when a step command fails to execute during an acceptance test.
 * 
 * Wraps the underlying exception with context about which step failed,
 * making test failures easier to diagnose and report.
 */
public class StepExecutionException extends RuntimeException {
    private final StepCommand command;

    public StepExecutionException(StepCommand command, Throwable cause) {
        super(formatMessage(command), cause);
        this.command = command;
    }

    private static String formatMessage(StepCommand command) {
        return String.format("[%s] step failed: %s",
                command.keyword().getLabel(),
                command.description());
    }

    /**
     * Get the command that failed.
     */
    public StepCommand getCommand() {
        return command;
    }
}
