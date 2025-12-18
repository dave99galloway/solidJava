package com.playground.solidjava.framework;

import com.playground.solidjava.typesafemapmap.ITypeSafeMapMap;
import com.playground.solidjava.typesafemapmap.TypeSafeMapMap;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * Maintains context and state across DSL steps.
 * Allows steps to share data without polluting the test class with state
 * variables.
 * 
 * Delegates to a TypeSafeMapMap instance for type-safe storage.
 */
public class StepContext implements ITypeSafeMapMap {
    private final TypeSafeMapMap delegate = new TypeSafeMapMap();

    @Override
    public <K, V> void put(@Nonnull K key, @Nonnull V value) {
        delegate.put(key, value);
    }

    @Override
    public <K, V> void put(@Nonnull K key, @Nonnull V value, @Nonnull Class<V> storageType) {
        delegate.put(key, value, storageType);
    }

    @Override
    @Nonnull
    public <K, V> V get(@Nonnull K key, @Nonnull Class<V> type) {
        return delegate.get(key, type);
    }

    @Override
    @Nonnull
    public <K, V> V get(@Nonnull K key) {
        return delegate.get(key);
    }

    @Override
    @Nonnull
    public <K, V> Map<K, V> allOfType(@Nonnull Class<V> type) {
        return delegate.allOfType(type);
    }

    @Override
    @Nonnull
    public <V, R> List<R> mapValues(@Nonnull Class<V> type, @Nonnull ValueAction<V, R> action) {
       return delegate.mapValues(type, action);
    }

    @Override
    @Nonnull
    public <V, R> List<R> mapEntries(@Nonnull Class<V> type, @Nonnull EntryAction<V, R> action) {
        return delegate.mapEntries(type, action);
    }

    @Override
    @Nonnull
    public <K, V> Map<K, V> mutableEntriesOfType(@Nonnull Class<V> type) {
        return delegate.mutableEntriesOfType(type);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    public void reset() {
        delegate.clear();
    }

}
