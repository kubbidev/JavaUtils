package com.kubbidev.java.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The Manager interface represents a generic manager responsible for handling objects of type T
 * with unique identifiers of type I. It provides various methods to manage and interact with these objects.
 *
 * @param <I> The type of the unique identifier for the objects.
 * @param <C> The common base type for the managed objects.
 * @param <T> The specific type of the objects managed by the manager, extending the common base type C.
 */
public interface Manager<I, C, T extends C> extends Function<I, T> {

    /**
     * Retrieves a map containing all the managed objects with their respective identifiers.
     *
     * @return A map with the unique identifiers (I) as keys and the managed objects (T) as values.
     */
    @NotNull Map<I, T> getAll();

    /**
     * Retrieves the managed object with the specified identifier (id).
     * If the object is not currently managed, it creates and registers a new instance with the given id.
     *
     * @param id The unique identifier of the object to retrieve or create if not loaded.
     * @return The managed object associated with the specified identifier (id).
     */
    @NotNull T getOrMake(@NotNull I id);

    /**
     * Retrieves the managed object with the specified identifier (id) if it is currently loaded.
     * If the object is not loaded, it returns null.
     *
     * @param id The unique identifier of the object to retrieve.
     * @return The managed object associated with the specified identifier (id) if loaded; otherwise, null.
     */
    @Nullable T getIfLoaded(@NotNull I id);

    /**
     * Performs a consumer operation on the loaded object with the specified identifier (id).
     * If the object is not loaded, the consumer operation is not performed.
     *
     * @param id       The unique identifier of the object to apply the consumer operation on.
     * @param consumer The consumer operation to be performed on the loaded object.
     */
    void ifLoaded(@NotNull I id, @NotNull Consumer<T> consumer);

    /**
     * Checks if the object with the specified identifier (id) is currently loaded.
     *
     * @param id The unique identifier of the object to check.
     * @return true if the object is loaded; otherwise, false.
     */
    boolean isLoaded(@NotNull I id);

    /**
     * Unloads the object with the specified identifier (id) from the manager.
     * If the object is not currently managed, this method does nothing.
     *
     * @param id The unique identifier of the object to unload.
     */
    void unload(@NotNull I id);

    /**
     * Retains only the objects whose identifiers (ids) are present in the given collection.
     * All other objects will be unloaded from the manager.
     *
     * @param ids A collection of unique identifiers to retain.
     */
    void retainAll(@NotNull Collection<I> ids);
}