package com.kubbidev.java.util;

import java.util.*;

public final class CollectionsUtil {

    private CollectionsUtil() {
        throw new AssertionError("No com.kubbidev.java.util.CollectionsUtil instances for you!");
    }

    /**
     * Splits a List into sublists of a specified target size.
     *
     * @param <T>         The type of elements in the List.
     * @param list        The List to split.
     * @param targetSize  The size of each sublist.
     * @return A List of sublists, each containing elements from the original List up to the target size.
     */
    public static <T> List<List<T>> split(List<T> list, int targetSize) {
        List<List<T>> lists = new ArrayList<>();
        if (targetSize <= 0)
            return lists;

        for (int i = 0; i < list.size(); i += targetSize) {
            lists.add(list.subList(i, Math.min(i + targetSize, list.size())));
        }
        return lists;
    }
}