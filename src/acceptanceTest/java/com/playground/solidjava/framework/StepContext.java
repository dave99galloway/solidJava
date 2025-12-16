package com.playground.solidjava.framework;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maintains context and state across DSL steps.
 * Allows steps to share data without polluting the test class with state variables.
 * 
 * Uses a type-safe map implementation where each type gets its own submap.
 * This provides compile-time type safety and prevents type mismatches.
 */
public class StepContext {
    private final Map<Type, Map<String, Object>> typeMap = new ConcurrentHashMap<>();

    /**
     * Store a value in the context for retrieval in subsequent steps.
     */
    public <T> StepContext put(String key, T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot store null value");
        }
        Class<?> valueType = value.getClass();
        Map<String, Object> submap = typeMap.computeIfAbsent(
            valueType,
            k -> new ConcurrentHashMap<>()
        );
        submap.put(key, value);
        return this;
    }

    /**
     * Retrieve a value from context with type safety.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Map<String, Object> submap = typeMap.get(type);
        if (submap == null) {
            throw new IllegalStateException(
                String.format("No values of type %s are stored in context", type.getSimpleName())
            );
        }
        Object value = submap.get(key);
        if (value == null) {
            throw new IllegalStateException(
                String.format("Context key not found: %s (type: %s)", key, type.getSimpleName())
            );
        }
        return (T) value;
    }

    /**
     * Retrieve a value from context with type safety, returning Optional.
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> find(String key, Class<T> type) {
        Map<String, Object> submap = typeMap.get(type);
        if (submap == null) {
            return Optional.empty();
        }
        Object value = submap.get(key);
        return Optional.ofNullable((T) value);
    }

    /**
     * Get all values of a specific type from the context.
     */
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> allOfType(Class<T> type) {
        Map<String, Object> submap = typeMap.get(type);
        if (submap == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap((Map<String, T>) (Map<?, ?>) submap);
    }

    /**
     * Check if a key exists for a specific type.
     */
    public boolean containsKey(String key, Class<?> type) {
        Map<String, Object> submap = typeMap.get(type);
        return submap != null && submap.containsKey(key);
    }

    /**
     * Clear all context data.
     */
    public void reset() {
        typeMap.clear();
    }
}
