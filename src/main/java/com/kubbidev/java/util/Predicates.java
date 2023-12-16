package com.kubbidev.java.util;

import com.google.common.collect.Range;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Predicate;

public final class Predicates {

    private Predicates() {
        throw new AssertionError("No com.kubbidev.java.util.Predicates instances for you!");
    }

    // Predicates for always returning true or false

    @SuppressWarnings("rawtypes")
    private static final Predicate FALSE = new Predicate() {
        // Implementation of a predicate that always returns false.

        @Override
        public boolean test(Object o) {
            return false;
        }

        // Methods for combining predicates

        @Override
        public @NotNull Predicate and(@NotNull Predicate other) {
            return this;
        }

        @Override
        public @NotNull Predicate or(@NotNull Predicate other) {
            return other;
        }

        @Override
        public @NotNull Predicate negate() {
            return TRUE;
        }
    };

    @SuppressWarnings("rawtypes")
    private static final Predicate TRUE = new Predicate() {
        // Implementation of a predicate that always returns true.

        @Override
        public boolean test(@NotNull Object o) {
            return true;
        }

        // Methods for combining predicates

        @Override
        public @NotNull Predicate and(@NotNull Predicate other) {
            return other;
        }

        @Override
        public @NotNull Predicate or(@NotNull Predicate other) {
            return this;
        }

        @Override
        public @NotNull Predicate negate() {
            return FALSE;
        }
    };

    /**
     * Returns a predicate that always evaluates to false.
     *
     * @param <T> The type of the elements to be tested by the predicate.
     * @return A predicate that always returns false.
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> alwaysFalse() {
        return FALSE;
    }

    /**
     * Returns a predicate that always evaluates to true.
     *
     * @param <T> The type of the elements to be tested by the predicate.
     * @return A predicate that always returns true.
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> alwaysTrue() {
        return TRUE;
    }

    // Predicates for numeric ranges

    /**
     * Returns a predicate that tests if the given integer is not within the specified range (inclusive).
     *
     * @param start The start of the range (inclusive).
     * @param end   The end of the range (inclusive).
     * @return A predicate that tests if the given integer is not within the specified range.
     */
    public static Predicate<Integer> notInRange(int start, int end) {
        Range<Integer> range = Range.closed(start, end);
        return value -> !range.contains(value);
    }

    /**
     * Returns a predicate that tests if the given integer is within the specified range (inclusive).
     *
     * @param start The start of the range (inclusive).
     * @param end   The end of the range (inclusive).
     * @return A predicate that tests if the given integer is within the specified range.
     */
    public static Predicate<Integer> inRange(int start, int end) {
        Range<Integer> range = Range.closed(start, end);
        return range::contains;
    }

    // Predicates for comparing objects

    /**
     * Returns a predicate that tests if the given object is not equal to the specified object.
     *
     * @param <T> The type of the elements to be tested by the predicate.
     * @param t   The object to compare against.
     * @return A predicate that tests if the given object is not equal to the specified object.
     */
    public static <T> Predicate<T> not(T t) {
        return obj -> !t.equals(obj);
    }

    /**
     * Returns a predicate that tests if the given object is equal to the specified object.
     *
     * @param <T> The type of the elements to be tested by the predicate.
     * @param t   The object to compare against.
     * @return A predicate that tests if the given object is equal to the specified object.
     */
    public static <T> Predicate<T> is(T t) {
        return t::equals;
    }

    // Predicates for string operations

    /**
     * Returns a predicate that tests if a string starts with the specified prefix (case-insensitive).
     *
     * @param prefix The prefix to compare with the start of the string.
     * @return A predicate that tests if a string starts with the specified prefix (case-insensitive).
     */
    public static Predicate<String> startsWithIgnoreCase(String prefix) {
        return string -> {
            if (string.length() < prefix.length()) {
                return false;
            }
            return string.regionMatches(true, 0, prefix, 0, prefix.length());
        };
    }

    /**
     * Returns a predicate that tests if a string contains the specified substring (case-insensitive).
     *
     * @param substring The substring to search for in the string.
     * @return A predicate that tests if a string contains the specified substring (case-insensitive).
     */
    public static Predicate<String> containsIgnoreCase(String substring) {
        return string -> {
            if (string.length() < substring.length()) {
                return false;
            }
            return string.toLowerCase(Locale.ROOT).contains(substring.toLowerCase(Locale.ROOT));
        };
    }
}