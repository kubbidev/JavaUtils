package com.kubbidev.java.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.stream.Collector;

public final class ImmutableCollectors {

    private ImmutableCollectors() {
        throw new AssertionError("No com.kubbidev.java.util.ImmutableCollectors instances for you!");
    }

    private static final Collector<Object, ImmutableList.Builder<Object>, ImmutableList<Object>> LIST = Collector.of(
            ImmutableList.Builder::new,
            ImmutableList.Builder::add,
            (l, r) -> l.addAll(r.build()),
            ImmutableList.Builder::build
    );

    private static final Collector<Object, ImmutableSet.Builder<Object>, ImmutableSet<Object>> SET = Collector.of(
            ImmutableSet.Builder::new,
            ImmutableSet.Builder::add,
            (l, r) -> l.addAll(r.build()),
            ImmutableSet.Builder::build
    );

    /**
     * Returns a collector that accumulates elements into an {@link ImmutableList}.
     *
     * @param <T> The type of elements in the list.
     * @return A collector that accumulates elements into an ImmutableList.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> toList() {
        return (Collector) LIST;
    }

    /**
     * Returns a collector that accumulates elements into an {@link ImmutableSet}.
     *
     * @param <T> The type of elements in the set.
     * @return A collector that accumulates elements into an ImmutableSet.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>> toSet() {
        return (Collector) SET;
    }

    /**
     * Returns a collector that accumulates elements into an {@link ImmutableSet} of enum constants.
     *
     * @param <T>   The type of enum elements.
     * @param clazz The class of the enum type.
     * @return A collector that accumulates elements into an ImmutableSet of enum constants.
     */
    public static <T extends Enum<T>> Collector<T, EnumSet<T>, ImmutableSet<T>> toEnumSet(Class<T> clazz) {
        return Collector.of(
                () -> EnumSet.noneOf(clazz),
                EnumSet::add,
                (l, r) -> { l.addAll(r); return l; },
                ImmutableSet::copyOf
        );
    }

    /**
     * Returns a collector that accumulates elements into an {@link ImmutableSortedSet} using natural order.
     *
     * @param <E> The type of elements in the sorted set.
     * @return A collector that accumulates elements into an ImmutableSortedSet using natural order.
     */
    public static <E extends Comparable<? super E>> Collector<E, ?, ImmutableSortedSet<E>> toSortedSet() {
        return Collector.of(
                ImmutableSortedSet::<E>naturalOrder,
                ImmutableSortedSet.Builder::add,
                (l, r) -> l.addAll(r.build()),
                ImmutableSortedSet.Builder::build
        );
    }

    /**
     * Returns a collector that accumulates elements into an {@link ImmutableSortedSet} using the specified comparator.
     *
     * @param <E>        The type of elements in the sorted set.
     * @param comparator The comparator to be used for sorting the elements.
     * @return A collector that accumulates elements into an ImmutableSortedSet using the specified comparator.
     */
    public static <E> Collector<E, ?, ImmutableSortedSet<E>> toSortedSet(Comparator<? super E> comparator) {
        return Collector.of(
                () -> new ImmutableSortedSet.Builder<E>(comparator),
                ImmutableSortedSet.Builder::add,
                (l, r) -> l.addAll(r.build()),
                ImmutableSortedSet.Builder::build
        );
    }

    /**
     * Returns a collector that accumulates elements into an {@link ImmutableMap} with the specified key and value mappers.
     *
     * @param <T>        The type of elements in the source stream.
     * @param <K>        The type of keys in the map.
     * @param <V>        The type of values in the map.
     * @param keyMapper  A mapping function to extract keys from the input elements.
     * @param valueMapper A mapping function to extract values from the input elements.
     * @return A collector that accumulates elements into an ImmutableMap with the specified key and value mappers.
     */
    public static <T, K, V> Collector<T, ImmutableMap.Builder<K, V>, ImmutableMap<K, V>> toMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends V> valueMapper) {
        return Collector.of(
                ImmutableMap.Builder<K, V>::new,
                (r, t) -> r.put(keyMapper.apply(t), valueMapper.apply(t)),
                (l, r) -> l.putAll(r.build()),
                ImmutableMap.Builder::build
        );
    }

    /**
     * Returns a collector that accumulates elements into an {@link ImmutableMap} with the specified key and value mappers
     * for enum keys.
     *
     * @param <T>        The type of elements in the source stream.
     * @param <K>        The enum key type.
     * @param <V>        The type of values in the map.
     * @param clazz      The class of the enum key type.
     * @param keyMapper  A mapping function to extract keys from the input elements.
     * @param valueMapper A mapping function to extract values from the input elements.
     * @return A collector that accumulates elements into an ImmutableMap with the specified key and value mappers
     * for enum keys.
     */
    public static <T, K extends Enum<K>, V> Collector<T, EnumMap<K, V>, ImmutableMap<K, V>> toEnumMap(
            Class<K> clazz,
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends V> valueMapper) {
        return Collector.of(
                () -> new EnumMap<>(clazz),
                (r, t) -> r.put(keyMapper.apply(t), valueMapper.apply(t)),
                (l, r) -> { l.putAll(r); return l; },
                ImmutableMap::copyOf
        );
    }
}