package com.playground.solidjava.framework;

import java.util.*;

/**
 * Maintains context and state across DSL steps.
 * Allows steps to share data without polluting the test class with state
 * variables.
 */
public class StepContext {
    private final Map<String, Object> contextData = new LinkedHashMap<>();

    /**
     * Store a value in the context for retrieval in subsequent steps.
     */
    public StepContext put(String key, Object value) {
        contextData.put(key, value);
        return this;
    }

    /**
     * Retrieve a value from context with type safety.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = contextData.get(key);
        if (value == null) {
            throw new IllegalStateException("Context key not found: " + key);
        }
        if (!type.isInstance(value)) {
            throw new ClassCastException(
                    String.format("Expected %s but got %s for key: %s",
                            type.getSimpleName(),
                            value.getClass().getSimpleName(),
                            key));
        }
        return (T) value;
    }

    /**
     * Retrieve a value from context with type safety, returning Optional.
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> find(String key, Class<T> type) {
        Object value = contextData.get(key);
        if (value == null) {
            return Optional.empty();
        }
        if (!type.isInstance(value)) {
            throw new ClassCastException(
                    String.format("Expected %s but got %s for key: %s",
                            type.getSimpleName(),
                            value.getClass().getSimpleName(),
                            key));
        }
        return Optional.of((T) value);
    }

    /**
     * Clear all context data.
     */
    public void reset() {
        contextData.clear();
    }
}
