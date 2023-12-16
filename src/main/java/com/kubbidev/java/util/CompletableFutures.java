package com.kubbidev.java.util;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Stream;

public final class CompletableFutures {

    private CompletableFutures() {
        throw new AssertionError("No com.kubbidev.java.util.CompletableFutures instances for you!");
    }

    /**
     * Creates a collector to combine multiple CompletableFuture instances into a single CompletableFuture<Void>.
     *
     * @param <T> The type of the CompletableFuture instances.
     * @return The collector to combine CompletableFuture instances.
     */
    public static <T extends CompletableFuture<?>> Collector<T, ImmutableList.Builder<T>, CompletableFuture<Void>> collector() {
        return Collector.of(
                ImmutableList.Builder::new,
                ImmutableList.Builder::add,
                (l, r) -> l.addAll(r.build()),
                builder -> allOf(builder.build())
        );
    }

    /**
     * Combines multiple CompletableFuture instances into a single CompletableFuture that completes when all of them complete.
     *
     * @param futures The Stream of CompletableFuture instances to combine.
     * @return A CompletableFuture<Void> that completes when all CompletableFuture instances in the Stream complete.
     */
    public static CompletableFuture<Void> allOf(Stream<? extends CompletableFuture<?>> futures) {
        CompletableFuture<?>[] arr = futures.toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(arr);
    }

    /**
     * Combines multiple CompletableFuture instances into a single CompletableFuture that completes when all of them complete.
     *
     * @param futures The Collection of CompletableFuture instances to combine.
     * @return A CompletableFuture<Void> that completes when all CompletableFuture instances in the Collection complete.
     */
    public static CompletableFuture<Void> allOf(Collection<? extends CompletableFuture<?>> futures) {
        CompletableFuture<?>[] arr = futures.toArray(new CompletableFuture[0]);
        return CompletableFuture.allOf(arr);
    }
}