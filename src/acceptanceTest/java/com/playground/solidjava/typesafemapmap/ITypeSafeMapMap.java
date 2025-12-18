package com.playground.solidjava.typesafemapmap;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * Interface for a type-safe map implementation.
 * 
 * Each type is stored in its own submap, providing type safety and preventing
 * type mismatches. Implementations must provide put/get methods for storing and
 * retrieving values, along with utility methods for working with typed submaps.
 */
public interface ITypeSafeMapMap {

    /**
     * Store a value with its type as key.
     * 
     * @param <V>   the type of the value
     * @param value the value to store (must not be null)
     */
    @SuppressWarnings("unchecked")
    default <V> void put(@Nonnull V value) {
        put((Class<V>) value.getClass(), value);
    }

    /**
     * Store a key value pair.
     * 
     * @param key   the key to store the value under (must not be null)
     * @param value the value to store (must not be null)
     * @param <K>   the type of the key
     * @param <V>   the type of the value
     */
    <K, V> void put(@Nonnull K key, @Nonnull V value);

    /**
     * Store a value under an explicitly specified type.
     * 
     * Use this overload when storing values as an interface, abstract class, or
     * other supertype that differs from the runtime type. This allows type-safe
     * retrieval using the specified type rather than the actual runtime type.
     * 
     * @param key         the key to store the value under (must not be null)
     * @param value       the value to store (must not be null)
     * @param storageType the type to use for storage and retrieval (must not be
     *                    null)
     * @param <K>         the type of the key
     * @param <V>         the type of the value
     */
    <K, V> void put(@Nonnull K key, @Nonnull V value, @Nonnull Class<V> storageType);

    /**
     * Retrieve a value by key and type.
     * 
     * @param key  the key to retrieve (must not be null)
     * @param type the type of the value (must not be null)
     * @param <K>  the type of the key
     * @param <V>  the type of the value
     * @return the value stored under the given key (never null)
     * @throws IllegalStateException if the type or key is not found
     */
    @Nonnull
    <K, V> V get(@Nonnull K key, @Nonnull Class<V> type);

    /**
     * Retrieve a value by key, with type inferred from the assignment context.
     * This allows natural type inference: {@code int value = get("key");}
     * 
     * Note: Due to Java's type erasure, this relies on the type recorded when the
     * value
     * was stored. The assignment target type does not affect runtime retrieval.
     * For the value at this key to be safely cast to the inferred type V, the
     * previously
     * stored value must actually be of that type. Each key can only store a single
     * value
     * (though that value's type can change if a new value is stored with the same
     * key).
     * 
     * @param key the key to retrieve (must not be null)
     * @param <K> the type of the key
     * @param <V> the type of the value, inferred from context
     * @return the value stored under the given key (never null)
     * @throws IllegalStateException if the key is not found
     */
    @Nonnull
    <K, V> V get(@Nonnull K key);

    /**
     * Get all values of a specific type from the context.
     * 
     * @param type the type to retrieve entries for (must not be null)
     * @param <K>  the type of the keys
     * @param <V>  the type of the values
     * @return an unmodifiable map of all values of the specified type (never null)
     */
    @Nonnull
    <K, V> Map<K, V> allOfType(@Nonnull Class<V> type);

    /**
     * Map each value of a specific type through an action function.
     * 
     * @param type   the type to iterate over (must not be null)
     * @param action the mapping function to apply to each value (must not be null)
     * @param <V>    the type of the values
     * @param <R>    the return type of the mapping function
     * @return a list of results from applying the mapping function to each value
     *         (never null)
     */
    @Nonnull
    <V, R> List<R> mapValues(@Nonnull Class<V> type, @Nonnull ValueAction<V, R> action);

    /**
     * Map over entries of a specific type.
     * 
     * @param type   the type to iterate over (must not be null)
     * @param action the action to apply to each entry (must not be null)
     * @param <V>    the type of the values
     * @param <R>    the return type of the action
     * @return a list of results from applying the action to each entry (never null)
     */
    @Nonnull
    <V, R> List<R> mapEntries(@Nonnull Class<V> type, @Nonnull EntryAction<V, R> action);

    /**
     * Get a mutable defensive copy of all entries of a specific type.
     * 
     * Modifications to the returned map will not affect the internal state.
     * Use this when you need mutable access to the entries.
     * 
     * @param type the type to retrieve entries for (must not be null)
     * @param <K>  the type of the keys
     * @param <V>  the type of the values
     * @return a mutable copy of all entries of the specified type (never null)
     */
    @Nonnull
    <K, V> Map<K, V> mutableEntriesOfType(@Nonnull Class<V> type);

    /**
     * Clear all stored data.
     */
    void clear();

    /**
     * Functional interface for actions that operate on values.
     */
    @FunctionalInterface
    interface ValueAction<V, R> {
        R apply(V value);
    }

    /**
     * Functional interface for actions that operate on entries.
     */
    @FunctionalInterface
    interface EntryAction<V, R> {
        R apply(Map.Entry<?, V> entry);
    }
}
