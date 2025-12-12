package com.playground.solidjava.dip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Dependency Inversion Principle: Another low-level implementation of Logger.
 * Can be swapped with ConsoleLogger without changing high-level code.
 */
public class FileLogger implements Logger {
    private final Path logFile;

    public FileLogger(Path logFile) {
        this.logFile = logFile;
    }

    @Override
    public void log(String message) {
        try {
            Files.writeString(logFile, "[FILE] " + message + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }

    @Override
    public void error(String message, Throwable throwable) {
        try {
            String errorLog = "[ERROR] " + message + ": " + throwable.getMessage() + "\n";
            Files.writeString(logFile, errorLog, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write error log: " + e.getMessage());
        }
    }
}
