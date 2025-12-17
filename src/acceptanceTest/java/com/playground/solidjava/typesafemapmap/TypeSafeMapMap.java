package com.playground.solidjava.typesafemapmap;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of ITypeSafeMapMap using a nested map structure with per-type
 * indexing.
 * 
 * Note: Due to Java's type erasure, each key can store only a single value at a
 * time.
 * Unlike Kotlin's reified generics, Java cannot distinguish between different
 * types at
 * runtime for the same key. If a new value is stored with an existing key but a
 * different
 * type, the previous entry is automatically removed.
 * 
 * Thread-safe using ConcurrentHashMap at all levels.
 */
public class TypeSafeMapMap implements ITypeSafeMapMap {
    private final Map<Type, Map<?, ?>> map = new ConcurrentHashMap<>();
    private final Map<Object, Type> keyToType = new ConcurrentHashMap<>();
    private final Validators validators = new Validators();

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> void put(@Nonnull K key, @Nonnull V value) {
        put(key, value, (Class<V>) value.getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> void put(@Nonnull K key, @Nonnull V value, @Nonnull Class<V> storageType) {
        Type valueType = storageType;

        // If key already exists with a different type, remove the old entry
        keyToType.merge(key, valueType, (existingType, newType) -> {
            if (!existingType.equals(newType)) {
                map.computeIfPresent(existingType, (type, oldSubmap) -> {
                    ((Map<K, ?>) oldSubmap).remove(key);
                    return oldSubmap;
                });
            }
            return newType;
        });

        // Get or create the submap for this type
        Map<K, V> submap = (Map<K, V>) map.computeIfAbsent(valueType, k -> new ConcurrentHashMap<>());
        submap.put(key, value);
    }

    @Override
    @Nonnull
    public <K, V> V get(@Nonnull K key, @Nonnull Class<V> type) {
        return validators.requireValue(key, type);
    }

    @Override
    @Nonnull
    public <K, V> V get(@Nonnull K key) {
        return validators.requireValue(key);
    }

    @Override
    @SuppressWarnings({ "unchecked", "null" })
    @Nonnull
    public <K, V> Map<K, V> allOfType(@Nonnull Class<V> type) {
        return Collections.unmodifiableMap((Map<K, V>) map.getOrDefault(type, Collections.emptyMap()));
    }

    @Override
    @SuppressWarnings("unchecked")
    @Nonnull
    public <K, V> Map<K, V> mutableEntriesOfType(@Nonnull Class<V> type) {
        return new ConcurrentHashMap<>((Map<K, V>) map.getOrDefault(type, Collections.emptyMap()));
    }

    @Override
    @SuppressWarnings({ "unchecked", "null" })
    @Nonnull
    public <V, R> List<R> mapValues(@Nonnull Class<V> type, @Nonnull ValueAction<V, R> action) {
        return ((Map<?, V>) map.getOrDefault(type, Collections.emptyMap()))
                .values().stream()
                .map(action::apply)
                .toList();
    }

    @Override
    @SuppressWarnings({ "unchecked", "null" })
    @Nonnull
    public <V, R> List<R> mapEntries(@Nonnull Class<V> type, @Nonnull EntryAction<V, R> action) {
        return ((Map<?, V>) map.getOrDefault(type, Collections.emptyMap()))
                .entrySet().stream()
                .map(action::apply)
                .toList();
    }

    @Override
    public void clear() {
        map.clear();
        keyToType.clear();
    }

    private class Validators {
        @Nonnull
        @SuppressWarnings("unchecked")
        <K, V> Map<K, V> requireSubmap(Class<?> type) {
            Map<K, V> submap = (Map<K, V>) map.get(type);
            if (submap == null)
                throw new IllegalStateException(
                        String.format("No values of type %s were found", type.getSimpleName()));
            return submap;
        }

        @Nonnull
        <K, V> V requireValue(@Nonnull K key, @Nonnull Class<?> type) {
            Map<K, V> submap = requireSubmap(type);
            V value = submap.get(key);
            if (value == null)
                throw new IllegalStateException(
                        String.format("Key not found: %s (type: %s)", key, type.getSimpleName()));
            return value;
        }

        @Nonnull
        <K, V> V requireValue(@Nonnull K key) {
            Type valueType = keyToType.get(key);
            if (valueType == null)
                throw new IllegalStateException(String.format("Key not found: %s", key));
            return requireValue(key, (Class<?>) valueType);
        }
    }
}
