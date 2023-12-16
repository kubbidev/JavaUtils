package com.kubbidev.java.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * A grab-bag of useful utility methods.
 */
public final class ObjectUtil {

    private static final float FLOAT_ZERO = 0.0F;
    private static final double DOUBLE_ZERO = 0.0D;
    private static final long LONG_ZERO = 0L;
    private static final int INT_ZERO = 0;

    private ObjectUtil() {
        throw new AssertionError("No com.kubbidev.java.util.Objects instances for you!");
    }

    /**
     * Check that the given varargs is not null and does not contain elements
     * null elements.
     * <p>
     * If it is, throws {@link NullPointerException}.
     * Otherwise, returns the argument.
     */
    @SafeVarargs
    public static <T> @NotNull T[] deepRequireNonNull(T... varargs) {
        if (varargs == null) {
            throw new NullPointerException();
        }

        for (T element : varargs) {
            if (element == null) {
                throw new NullPointerException();
            }
        }
        return varargs;
    }

    /**
     * Check that the given varargs is not null and does not contain elements
     * null elements.
     * <p>
     * If it is, throws {@link NullPointerException}.
     * Otherwise, returns the argument.
     */
    @SafeVarargs
    public static <T> @NotNull T[] deepRequireNonNull(String text, T... varargs) {
        if (varargs == null) {
            throw new NullPointerException(text);
        }

        for (T element : varargs) {
            if (element == null) {
                throw new NullPointerException(text);
            }
        }
        return varargs;
    }

    /**
     * Checks that the given argument is not null. If it is, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static <T> @NotNull T requireNonNullWithIAE(@Nullable T arg, String paramName) throws IllegalArgumentException {
        if (arg == null) {
            throw new IllegalArgumentException("Param '" + paramName + "' must not be null");
        }
        return arg;
    }

    /**
     * Checks that the given argument is not null. If it is, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     *
     * @param <T> type of the given argument value.
     * @param name of the parameter, belongs to the exception message.
     * @param index of the array, belongs to the exception message.
     * @param value to check.
     * @return the given argument value.
     * @throws IllegalArgumentException if value is null.
     */
    public static <T> @NotNull T requireNonNullArrayParam(@Nullable T value, int index, String name) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(
                    "Array index " + index + " of parameter '" + name + "' must not be null");
        }
        return value;
    }

    /**
     * Checks that the given argument is strictly positive. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static int requirePositive(int i, String name) {
        if (i <= INT_ZERO) {
            throw new IllegalArgumentException(name + " : " + i + " (expected: > 0)");
        }
        return i;
    }

    /**
     * Checks that the given argument is strictly positive. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static long requirePositive(long l, String name) {
        if (l <= LONG_ZERO) {
            throw new IllegalArgumentException(name + " : " + l + " (expected: > 0)");
        }
        return l;
    }

    /**
     * Checks that the given argument is strictly positive. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static double requirePositive(double d, String name) {
        if (d <= DOUBLE_ZERO) {
            throw new IllegalArgumentException(name + " : " + d + " (expected: > 0)");
        }
        return d;
    }

    /**
     * Checks that the given argument is strictly positive. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static float requirePositive(float f, String name) {
        if (f <= FLOAT_ZERO) {
            throw new IllegalArgumentException(name + " : " + f + " (expected: > 0)");
        }
        return f;
    }

    /**
     * Checks that the given argument is positive or zero. If it is not , throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static int requirePositiveOrZero(int i, String name) {
        if (i < INT_ZERO) {
            throw new IllegalArgumentException(name + " : " + i + " (expected: >= 0)");
        }
        return i;
    }

    /**
     * Checks that the given argument is positive or zero. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static long requirePositiveOrZero(long l, String name) {
        if (l < LONG_ZERO) {
            throw new IllegalArgumentException(name + " : " + l + " (expected: >= 0)");
        }
        return l;
    }

    /**
     * Checks that the given argument is positive or zero. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static double requirePositiveOrZero(double d, String name) {
        if (d < DOUBLE_ZERO) {
            throw new IllegalArgumentException(name + " : " + d + " (expected: >= 0)");
        }
        return d;
    }

    /**
     * Checks that the given argument is positive or zero. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static float requirePositiveOrZero(float f, String name) {
        if (f < FLOAT_ZERO) {
            throw new IllegalArgumentException(name + " : " + f + " (expected: >= 0)");
        }
        return f;
    }

    /**
     * Checks that the given argument is in range. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static int requireInRange(int i, int start, int end, String name) {
        if (i < start || i > end) {
            throw new IllegalArgumentException(name + ": " + i + " (expected: " + start + "-" + end + ")");
        }
        return i;
    }

    /**
     * Checks that the given argument is in range. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static long requireInRange(long l, long start, long end, String name) {
        if (l < start || l > end) {
            throw new IllegalArgumentException(name + ": " + l + " (expected: " + start + "-" + end + ")");
        }
        return l;
    }

    /**
     * Checks that the given argument is neither null nor empty.
     * If it is, throws {@link NullPointerException} or {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static <T> T[] requireNonEmpty(@Nullable T[] array, String name) {
        //No String concatenation for check
        if (requireNonNull(array, name).length == 0) {
            throw new IllegalArgumentException("Param '" + name + "' must not be empty");
        }
        return array;
    }

    /**
     * Checks that the given argument is neither null nor empty.
     * If it is, throws {@link NullPointerException} or {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static byte[] requireNonEmpty(byte[] array, String name) {
        //No String concatenation for check
        if (requireNonNull(array, name).length == 0) {
            throw new IllegalArgumentException("Param '" + name + "' must not be empty");
        }
        return array;
    }

    /**
     * Checks that the given argument is neither null nor empty.
     * If it is, throws {@link NullPointerException} or {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static char[] requireNonEmpty(char[] array, String name) {
        //No String concatenation for check
        if (requireNonNull(array, name).length == 0) {
            throw new IllegalArgumentException("Param '" + name + "' must not be empty");
        }
        return array;
    }

    /**
     * Checks that the given argument is neither null nor empty.
     * If it is, throws {@link NullPointerException} or {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static <T extends Collection<?>> @NotNull T requireNonEmpty(@Nullable T collection, String name) {
        //No String concatenation for check
        if (requireNonNull(collection, name).isEmpty()) {
            throw new IllegalArgumentException("Param '" + name + "' must not be empty");
        }
        return collection;
    }

    /**
     * Checks that the given argument is neither null nor empty.
     * If it is, throws {@link NullPointerException} or {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static @NotNull String requireNonEmpty(@Nullable String value, String name) {
        if (requireNonNull(value, name).isEmpty()) {
            throw new IllegalArgumentException("Param '" + name + "' must not be empty");
        }
        return value;
    }

    /**
     * Checks that the given argument is neither null nor empty.
     * If it is, throws {@link NullPointerException} or {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static <K, V, T extends Map<K, V>> @NotNull T requireNonEmpty(@Nullable T value, String name) {
        if (requireNonNull(value, name).isEmpty()) {
            throw new IllegalArgumentException("Param '" + name + "' must not be empty");
        }
        return value;
    }

    /**
     * Checks that the given argument is neither null nor empty.
     * If it is, throws {@link NullPointerException} or {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    public static @NotNull CharSequence requireNonEmpty(@Nullable CharSequence value, String name) {
        if (requireNonNull(value, name).isEmpty()) {
            throw new IllegalArgumentException("Param '" + name + "' must not be empty");
        }
        return value;
    }

    /**
     * Trims the given argument and checks whether it is neither null nor empty.
     * If it is, throws {@link NullPointerException} or {@link IllegalArgumentException}.
     * Otherwise, returns the trimmed argument.
     *
     * @param value to trim and check.
     * @param name of the parameter.
     * @return the trimmed (not the original) value.
     * @throws NullPointerException if value is null.
     * @throws IllegalArgumentException if the trimmed value is empty.
     */
    public static @NotNull String requireNonEmptyAfterTrim(@Nullable String value, String name) {
        String trimmed = requireNonNull(value, name).trim();
        return requireNonEmpty(trimmed, name);
    }

    /**
     * Resolves a possibly null Byte to a primitive byte, using a default value.
     * @param wrapper the wrapper
     * @param defaultValue the default value
     * @return the primitive value
     */
    public static byte byteValue(@Nullable Byte wrapper, byte defaultValue) {
        return wrapper != null ? wrapper : defaultValue;
    }

    /**
     * Resolves a possibly null Short to a primitive short, using a default value.
     * @param wrapper the wrapper
     * @param defaultValue the default value
     * @return the primitive value
     */
    public static short shortValue(@Nullable Short wrapper, short defaultValue) {
        return wrapper != null ? wrapper : defaultValue;
    }

    /**
     * Resolves a possibly null Integer to a primitive int, using a default value.
     * @param wrapper the wrapper
     * @param defaultValue the default value
     * @return the primitive value
     */
    public static int intValue(@Nullable Integer wrapper, int defaultValue) {
        return wrapper != null ? wrapper : defaultValue;
    }

    /**
     * Resolves a possibly null Long to a primitive long, using a default value.
     * @param wrapper the wrapper
     * @param defaultValue the default value
     * @return the primitive value
     */
    public static long longValue(@Nullable Long wrapper, long defaultValue) {
        return wrapper != null ? wrapper : defaultValue;
    }

    /**
     * Resolves a possibly null Float to a primitive float, using a default value.
     * @param wrapper the wrapper
     * @param defaultValue the default value
     * @return the primitive value
     */
    public static float floatValue(@Nullable Float wrapper, float defaultValue) {
        return wrapper != null ? wrapper : defaultValue;
    }

    /**
     * Resolves a possibly null Double to a primitive double, using a default value.
     * @param wrapper the wrapper
     * @param defaultValue the default value
     * @return the primitive value
     */
    public static double doubleValue(@Nullable Double wrapper, double defaultValue) {
        return wrapper != null ? wrapper : defaultValue;
    }
}
