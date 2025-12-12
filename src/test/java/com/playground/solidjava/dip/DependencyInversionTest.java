package com.playground.solidjava.dip;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Dependency Inversion Principle.
 * This test demonstrates that high-level modules (UserService) depend on abstractions (Logger),
 * not on concrete implementations (ConsoleLogger or FileLogger).
 * New logger implementations can be added without changing this test or UserService.
 */
@DisplayName("Dependency Inversion Principle Tests")
class DependencyInversionTest {
    private MockLogger mockLogger;

    @BeforeEach
    void setUp() {
        mockLogger = new MockLogger();
    }

    @Test
    @DisplayName("UserService should depend on Logger abstraction")
    void userServiceShouldDependOnLoggerAbstraction() {
        // Arrange
        UserService service = new UserService(mockLogger);

        // Act
        service.registerUser("john_doe");

        // Assert
        assertThat(mockLogger.getLoggedMessages()).contains("User registered: john_doe");
    }

    @Test
    @DisplayName("UserService should work with ConsoleLogger implementation")
    void userServiceShouldWorkWithConsoleLogger() {
        // Arrange
        Logger consoleLogger = new ConsoleLogger();
        UserService service = new UserService(consoleLogger);

        // Act & Assert
        assertThatCode(() -> service.registerUser("user123"))
                .doesNotThrowAnyException();
        assertThat(service.getUserCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("UserService can be swapped with different logger implementations")
    void userServiceCanBeSwappedWithDifferentLoggers() {
        // Arrange
        Logger mockLogger1 = new MockLogger();
        Logger mockLogger2 = new MockLogger();

        UserService service1 = new UserService(mockLogger1);
        UserService service2 = new UserService(mockLogger2);

        // Act
        service1.registerUser("user1");
        service2.registerUser("user2");

        // Assert
        assertThat(service1.getUserCount()).isEqualTo(1);
        assertThat(service2.getUserCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("UserService should handle invalid usernames")
    void userServiceShouldHandleInvalidUsernames() {
        // Arrange
        UserService service = new UserService(mockLogger);

        // Act - register with null username
        service.registerUser(null);

        // Assert - service handles error gracefully without throwing
        assertThat(service.getUserCount()).isEqualTo(0);
        assertThat(mockLogger.getErrorCount()).isGreaterThan(0);

        // Act - register with empty username
        mockLogger = new MockLogger(); // Reset for clean state
        service = new UserService(mockLogger);
        service.registerUser("");

        // Assert
        assertThat(service.getUserCount()).isEqualTo(0);
        assertThat(mockLogger.getErrorCount()).isGreaterThan(0);
    }

    @Test
    @DisplayName("UserService should log errors when registration fails")
    void userServiceShouldLogErrorsWhenRegistrationFails() {
        // Arrange
        UserService service = new UserService(mockLogger);

        // Act
        try {
            service.registerUser(null);
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Assert
        assertThat(mockLogger.getErrorCount()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Multiple registrations should be tracked")
    void multipleRegistrationsShouldBeTracked() {
        // Arrange
        UserService service = new UserService(mockLogger);

        // Act
        service.registerUser("user1");
        service.registerUser("user2");
        service.registerUser("user3");

        // Assert
        assertThat(service.getUserCount()).isEqualTo(3);
        assertThat(mockLogger.getLoggedMessages()).hasSize(3);
    }

    /**
     * Test helper: Validates that a logger correctly implements the Logger contract.
     * Dependency Inversion: abstracts away the concrete logger implementation.
     */
    private void assertLoggerWorksCorrectly(Logger logger) {
        assertThatCode(() -> logger.log("test message"))
                .doesNotThrowAnyException();

        assertThatCode(() -> logger.error("error message", new RuntimeException("test")))
                .doesNotThrowAnyException();
    }

    /**
     * Mock Logger implementation for testing.
     * Allows us to verify that UserService correctly depends on Logger abstraction.
     */
    static class MockLogger implements Logger {
        private final java.util.List<String> messages = new java.util.ArrayList<>();
        private int errorCount = 0;

        @Override
        public void log(String message) {
            messages.add(message);
        }

        @Override
        public void error(String message, Throwable throwable) {
            messages.add("[ERROR] " + message);
            errorCount++;
        }

        java.util.List<String> getLoggedMessages() {
            return new java.util.ArrayList<>(messages);
        }

        int getErrorCount() {
            return errorCount;
        }
    }
}
