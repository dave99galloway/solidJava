package com.playground.solidjava.dip;

/**
 * Dependency Inversion Principle: High-level modules depend on abstraction (Logger),
 * not on concrete implementations. Low-level modules implement this interface.
 */
public interface Logger {
    void log(String message);

    void error(String message, Throwable throwable);
}
