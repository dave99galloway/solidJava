package com.playground.solidjava.dip;

/**
 * Dependency Inversion Principle: This is a low-level module that implements Logger.
 * It can be replaced with another implementation without affecting high-level modules.
 */
public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("[CONSOLE] " + message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        System.err.println("[ERROR] " + message);
        throwable.printStackTrace();
    }
}
