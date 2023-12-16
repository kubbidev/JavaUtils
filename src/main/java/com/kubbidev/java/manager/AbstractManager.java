package com.kubbidev.java.manager;

import com.google.common.collect.ImmutableMap;
import com.kubbidev.java.util.LoadingMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An abstract manager class
 *
 * @param <I> the class used to identify each object held in this manager
 * @param <C> the super class being managed
 * @param <T> the implementation class this manager is "managing"
 */
public abstract class AbstractManager<I, C, T extends C> implements Manager<I, C, T> {

    private final LoadingMap<I, T> objects = LoadingMap.of(this);

    @Override
    public @NotNull Map<I, T> getAll() {
        return ImmutableMap.copyOf(this.objects);
    }

    @Override
    public @NotNull T getOrMake(@NotNull I id) {
        return this.objects.get(sanitizeIdentifier(id));
    }

    @Override
    public @Nullable T getIfLoaded(@NotNull I id) {
        return this.objects.getIfPresent(sanitizeIdentifier(id));
    }

    @Override
    public void ifLoaded(@NotNull I id, @NotNull Consumer<T> consumer) {
        T t = getIfLoaded(id);
        if (t != null)
            consumer.accept(t);
    }

    @Override
    public boolean isLoaded(@NotNull I id) {
        return this.objects.containsKey(sanitizeIdentifier(id));
    }

    @Override
    public void unload(@NotNull I id) {
        this.objects.remove(sanitizeIdentifier(id));
    }

    @Override
    public void retainAll(@NotNull Collection<I> ids) {
        this.objects.keySet().stream()
                .filter(g -> !ids.contains(g))
                .forEach(this::unload);
    }

    @NotNull
    protected I sanitizeIdentifier(@NotNull I i) {
        return i;
    }
}
