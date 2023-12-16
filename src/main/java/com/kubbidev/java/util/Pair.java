package com.kubbidev.java.util;

import java.util.Objects;

/**
 * The "Pair" class represents an immutable pair of two values, left and right.
 * It is used to store and retrieve two related values as a single unit.
 *
 * @param <L> The type of the left value.
 * @param <R> The type of the right value.
 */
public class Pair<L, R> {

    private final L left;
    private final R right;

    /**
     * Constructs a new "Pair" object with the given left and right values.
     *
     * @param left  The left value.
     * @param right The right value.
     */
    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left value of the pair.
     *
     * @return The left value of the pair.
     */
    public L getLeft() {
        return this.left;
    }

    /**
     * Returns the right value of the pair.
     *
     * @return The right value of the pair.
     */
    public R getRight() {
        return this.right;
    }

    /**
     * Creates a new "Pair" object with the specified left and right values.
     *
     * @param <L>   The type of the left value.
     * @param <R>   The type of the right value.
     * @param left  The left value of the pair.
     * @param right The right value of the pair.
     * @return A new "Pair" object with the specified left and right values.
     */
    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    /**
     * Compares this "Pair" object to another object for equality.
     * Two "Pair" objects are considered equal if their left and right values are equal.
     *
     * @param o The object to compare with this "Pair" object.
     * @return true if the given object is equal to this "Pair" object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(this.left, pair.left) && Objects.equals(this.right, pair.right);
    }
}