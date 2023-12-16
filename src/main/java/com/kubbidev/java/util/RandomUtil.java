package com.kubbidev.java.util;

import java.util.*;

public final class RandomUtil {

    private RandomUtil() {
        throw new AssertionError("No com.kubbidev.java.util.RandomUtil instances for you!");
    }

    /**
     * A shared instance of the Java Random class for generating random numbers.
     */
    public static final Random RANDOM = new Random();

    /**
     * Generates a random float value between 0 (inclusive) and 1 (exclusive).
     *
     * @return The random float value.
     */
    public static float get() {
        return RANDOM.nextFloat();
    }

    /**
     * Generates a random float value between 0 (inclusive) and 100 (exclusive), optionally normalized to 0-100 range.
     *
     * @param normalize If true, the generated value will be multiplied by 100 to get a value in the 0-100 range.
     * @return The random float value.
     */
    public static float get(boolean normalize) {
        float f = RandomUtil.get();
        if (normalize)
            f *= 100f;
        return f;
    }

    /**
     * Generates a random int value between 0 (inclusive) and the specified upper bound (exclusive).
     *
     * @param n The upper bound (exclusive) of the random value range.
     * @return The random int value.
     */
    public static int get(int n) {
        return RandomUtil.nextInt(n);
    }

    /**
     * Generates a random int value between the specified minimum and maximum values (inclusive).
     *
     * @param min The minimum value of the random range.
     * @param max The maximum value of the random range.
     * @return The random int value.
     */
    public static int get(int min, int max) {
        return min + (int) Math.floor(RANDOM.nextDouble() * (max - min + 1));
    }

    /**
     * Generates a random double value between 0 (inclusive) and the specified maximum value (exclusive).
     *
     * @param max The maximum value (exclusive) of the random range.
     * @return The random double value.
     */
    public static double getDouble(double max) {
        return getDouble(0, max);
    }

    /**
     * Generates a random double value between the specified minimum and maximum values (inclusive).
     *
     * @param min The minimum value of the random range.
     * @param max The maximum value of the random range.
     * @return The random double value.
     */
    public static double getDouble(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }

    /**
     * Generates a random double value between the specified minimum and maximum values (inclusive).
     * The generated value can be negative.
     *
     * @param min The minimum value of the random range.
     * @param max The maximum value of the random range.
     * @return The random double value.
     */
    public static double getDoubleNegative(double min, double max) {
        double range = max - min;
        double scaled = RANDOM.nextDouble() * range;
        return scaled + min;
    }

    /**
     * Randomly selects an int value from the provided array.
     *
     * @param list The array from which to select the value.
     * @return The randomly selected int value.
     */
    public static int get(int[] list) {
        return list[get(list.length)];
    }

    /**
     * Randomly selects an element from the provided list.
     *
     * @param list The list from which to select the element.
     * @param <E>  The type of elements in the list.
     * @return The randomly selected element from the list, or null if the list is empty.
     */
    public static <E> E get(List<E> list) {
        return list.isEmpty() ? null : list.get(get(list.size()));
    }

    /**
     * Randomly selects an element from the provided array.
     *
     * @param list The array from which to select the element.
     * @param <E>  The type of elements in the array.
     * @return The randomly selected element from the array.
     */
    public static <E> E get(E[] list) {
        return list[get(list.length)];
    }

    /**
     * Randomly selects a value from the provided map based on the values' probabilities.
     * The probability of each value is determined by its associated weight (a positive double value).
     * Values with weights less than or equal to 0 are excluded from selection.
     *
     * @param map The map containing the values and their weights (probabilities).
     * @param <T> The type of keys in the map.
     * @param <K> The type of values in the map.
     * @return The randomly selected value from the map, or null if the map is empty or all values have zero or negative weights.
     */
    public static <T, K> K getValue(Map<T, K> map) {
        List<K> keysAsArray = new ArrayList<>(map.values());
        return get(keysAsArray);
    }

    /**
     * Randomly selects a key from the provided map based on the keys' probabilities.
     * The probability of each value is determined by its associated weight (a positive double value).
     * Keys with weights less than or equal to 0 are excluded from selection.
     *
     * @param map The map containing the keys and their weights (probabilities).
     * @param <T> The type of keys in the map.
     * @param <K> The type of values in the map.
     * @return The randomly selected key from the map, or null if the map is empty or all keys have zero or negative weights.
     */
    public static <T, K> T getKey(Map<T, K> map) {
        List<T> keysAsArray = new ArrayList<>(map.keySet());
        return get(keysAsArray);
    }

    /**
     * Randomly selects a value from the provided map based on the values' probabilities represented by double weights.
     * The probability of each value is determined by its associated weight (a positive double value).
     * Values with weights less than or equal to 0 are excluded from selection.
     * This method allows selecting multiple values (specified by the 'amount' parameter) at once.
     *
     * @param map    The map containing the values and their weights (probabilities).
     * @param amount The number of values to randomly select from the map.
     * @param <T>    The type of keys in the map.
     * @return A list of randomly selected values from the map, or an empty list if the map is empty or all values have zero or negative weights.
     */
    public static <T> List<T> getDoubleMap(Map<T, Double> map, int amount) {
        // Remove values with non-positive weights
        map.values().removeIf(chance -> chance <= 0);

        if (map.isEmpty())
            return Collections.emptyList();

        List<T> list = new ArrayList<>();
        double total = map.values().stream().mapToDouble(d -> d).sum();

        for (int count = 0; count < amount; count++) {
            double index = RandomUtil.getDouble(0, total);
            double countWeight = 0;

            for (Map.Entry<T, Double> entry : map.entrySet()) {
                countWeight += entry.getValue();

                if (countWeight >= index) {
                    list.add(entry.getKey());
                    break;
                }
            }
        }

        return list;
    }

    /**
     * Determines whether a certain chance percentage is met.
     *
     * @param chance The chance percentage to test. Should be in the range of 1 to 100.
     * @return true if the chance is met, false otherwise.
     */
    public static boolean chance(int chance) {
        return chance >= 1 && (chance > 99 || nextInt(99) + 1 <= chance);
    }

    /**
     * Determines whether a certain chance percentage is met.
     *
     * @param chance The chance percentage to test. Should be in the range of 0.0 to 100.0.
     * @return true if the chance is met, false otherwise.
     */
    public static boolean chance(double chance) {
        return nextDouble() <= chance / 100;
    }

    /**
     * Generates a random int value between 0 (inclusive) and the specified upper bound (exclusive).
     *
     * @param n The upper bound (exclusive) of the random value range.
     * @return The random int value.
     */
    public static int nextInt(int n) {
        return (int) Math.floor(RANDOM.nextDouble() * n);
    }

    /**
     * Generates a random int value.
     *
     * @return The random int value.
     */
    public static int nextInt() {
        return RANDOM.nextInt();
    }

    /**
     * Generates a random double value between 0 (inclusive) and 1 (exclusive).
     *
     * @return The random double value.
     */
    public static double nextDouble() {
        return RANDOM.nextDouble();
    }

    /**
     * Generates a random double value from the standard Gaussian distribution (mean 0.0 and standard deviation 1.0).
     *
     * @return The random double value.
     */
    public static double nextGaussian() {
        return RANDOM.nextGaussian();
    }

    /**
     * Generates a random boolean value (true or false).
     *
     * @return The random boolean value.
     */
    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }
}
