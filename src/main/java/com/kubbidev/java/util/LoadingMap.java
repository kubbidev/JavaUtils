package com.kubbidev.java.util;

import com.google.common.collect.ForwardingMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * The "LoadingMap" class represents a map that automatically loads values on demand using a specified function.
 * It extends "ForwardingMap" to provide a convenient way to delegate map operations to an underlying map implementation.
 *
 * @param <K> The type of keys in the map.
 * @param <V> The type of values in the map.
 */
public class LoadingMap<K, V> extends ForwardingMap<K, V> implements Map<K, V> {

    /**
     * Creates a new "LoadingMap" with the provided map and function to load values.
     *
     * @param <K>      The type of keys in the map.
     * @param <V>      The type of values in the map.
     * @param map      The underlying map to store and retrieve values.
     * @param function The function used to compute and load values for missing keys.
     * @return A new "LoadingMap" instance with the specified map and loading function.
     */
    public static <K, V> LoadingMap<K, V> of(Map<K, V> map, Function<K, V> function) {
        return new LoadingMap<>(map, function);
    }

    /**
     * Creates a new "LoadingMap" with a default ConcurrentHashMap and the provided function to load values.
     * The default map implementation is thread-safe and suitable for concurrent access.
     *
     * @param <K>      The type of keys in the map.
     * @param <V>      The type of values in the map.
     * @param function The function used to compute and load values for missing keys.
     * @return A new "LoadingMap" instance with a ConcurrentHashMap and the specified loading function.
     */
    public static <K, V> LoadingMap<K, V> of(Function<K, V> function) {
        return of(new ConcurrentHashMap<>(), function);
    }

    private final Map<K, V> loadingMap;
    private final Function<K, V> function;

    /**
     * Constructs a new "LoadingMap" instance with the specified underlying map and loading function.
     *
     * @param loadingMap The underlying map to store and retrieve values.
     * @param function   The function used to compute and load values for missing keys.
     */
    private LoadingMap(Map<K, V> loadingMap, Function<K, V> function) {
        this.loadingMap = loadingMap;
        this.function = function;
    }

    /**
     * Returns the underlying map that stores the values in the "LoadingMap".
     * This method is overridden from "ForwardingMap" to provide map delegation.
     *
     * @return The underlying map implementation.
     */
    @Override
    protected @NotNull Map<K, V> delegate() {
        return this.loadingMap;
    }

    /**
     * Returns the value associated with the specified key, or computes and loads the value if the key is missing.
     * If the value for the given key is already present in the map, it is returned directly.
     * Otherwise, the value is computed using the loading function and stored in the map before being returned.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the specified key, or the loaded value if the key is missing.
     */
    public @Nullable V getIfPresent(K key) {
        return this.loadingMap.get(key);
    }

    /**
     * Retrieves the value associated with the specified key from the map.
     * If the value is already present in the map, it is returned.
     * If the value is not present, it is computed and loaded using the specified loading function.
     * The computed value is stored in the map before being returned.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the specified key, or the computed and loaded value if the key is missing.
     */
    @SuppressWarnings("unchecked")
    @Override
    public @NotNull V get(Object key) {
        V value = this.loadingMap.get(key);
        if (value != null) {
            return value;
        }
        return this.loadingMap.computeIfAbsent((K) key, this.function);
    }
}