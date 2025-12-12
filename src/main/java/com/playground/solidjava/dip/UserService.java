package com.playground.solidjava.dip;

/**
 * Dependency Inversion Principle: This high-level module depends on Logger abstraction,
 * not on concrete implementations. This makes it easy to swap Logger implementations.
 */
public class UserService {
    private final Logger logger;
    private int userCount = 0;

    public UserService(Logger logger) {
        this.logger = logger;
    }

    public void registerUser(String username) {
        try {
            if (username == null || username.isBlank()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            userCount++;
            logger.log("User registered: " + username);
        } catch (Exception e) {
            logger.error("Failed to register user: " + username, e);
        }
    }

    public int getUserCount() {
        return userCount;
    }
}
