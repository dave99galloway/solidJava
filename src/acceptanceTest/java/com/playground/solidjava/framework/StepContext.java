package com.playground.solidjava.framework;

import com.playground.solidjava.typesafemapmap.TypeSafeMapMap;
import javax.annotation.Nonnull;

/**
 * Maintains context and state across DSL steps.
 * Allows steps to share data without polluting the test class with state
 * variables.
 * 
 * Delegates to a TypeSafeMapMap instance for type-safe storage.
 * The type-safe map structure ensures each type gets its own submap,
 * providing compile-time type safety and preventing type mismatches.
 */
public class StepContext {
    private final TypeSafeMapMap delegate = new TypeSafeMapMap();

    /**
     * Store a value in the context for retrieval in subsequent steps.
     * Convenience method that delegates to the underlying map.
     */
    public <T> StepContext put(@Nonnull String key, @Nonnull T value) {
        delegate.put(key, value);
        return this;
    }

    /**
     * Retrieve a value from context with type safety.
     */
    @Nonnull
    public <T> T get(@Nonnull String key, @Nonnull Class<T> type) {
        return delegate.get(key, type);
    }

    /**
     * Retrieve a value from context with type inference from the assignment
     * context.
     * This allows natural code: {@code int result = context.get("result");}
     * 
     * Note: Due to Java's type erasure, each key can store only a single value.
     * The assignment target type does not affect runtime retrieval; the type is
     * determined
     * by what was previously stored with this key.
     * 
     * @param key the key to retrieve
     * @param <T> the type of the value, inferred from context
     * @return the value stored under the given key
     * @throws IllegalStateException if the key is not found
     */
    @Nonnull
    public <T> T get(@Nonnull String key) {
        return delegate.get(key);
    }

    /**
     * Clear all context data.
     */
    public void reset() {
        delegate.clear();
    }
}
