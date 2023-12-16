package com.kubbidev.java.util;

import java.util.*;
import java.util.stream.Collectors;

public final class MapUtil {

    private MapUtil() {
        throw new AssertionError("No com.kubbidev.java.util.MapUtil instances for you!");
    }

    /**
     * @param map The map to inverse.
     * @return The {@code map} inverted, keys become values and values become keys.
     */
    public static <K, V> Map<V, K> inverse(Map<K, V> map) {
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    /**
     * @param map        The map to sort.
     * @param comparator The {@link Comparator} to be used to compare stream elements.
     * @return A {@link LinkedHashMap} containing the elements of the {@code map} sorted using {@code comparator}.
     */
    public static <K, V> Map<K, V> sort(Map<K, V> map, Comparator<? super Map.Entry<K, V>> comparator) {
        return map.entrySet()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (value1, value2) -> value1,
                        LinkedHashMap::new));
    }

    /**
     * Sorts a Map by its values in ascending order.
     *
     * @param <K>  The type of keys in the Map.
     * @param <V>  The type of values in the Map, which must be Comparable.
     * @param map  The Map to sort.
     * @return A LinkedHashMap containing entries of the original Map, sorted by values in ascending order.
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet()).stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    /**
     * Sorts a Map by its values in descending order.
     *
     * @param <K>  The type of keys in the Map.
     * @param <V>  The type of values in the Map, which must be Comparable.
     * @param map  The Map to sort.
     * @return A LinkedHashMap containing entries of the original Map, sorted by values in descending order.
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueUpDown(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet()).stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .toList();

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
