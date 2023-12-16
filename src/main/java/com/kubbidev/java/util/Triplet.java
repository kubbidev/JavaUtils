package com.kubbidev.java.util;

import java.util.Objects;

/**
 * The "Triplet" class represents a tuple of three values: left, center, and right.
 * The class provides methods to access and create instances of triplets.
 *
 * @param <L> The type of the left value.
 * @param <C> The type of the center value.
 * @param <R> The type of the right value.
 */
public class Triplet<L, C, R> {

    private final L left;
    private final C center;
    private final R right;

    /**
     * Constructs a new triplet with the specified left, center, and right values.
     *
     * @param left   The left value of the triplet.
     * @param center The center value of the triplet.
     * @param right  The right value of the triplet.
     */
    private Triplet(L left, C center, R right) {
        this.left = left;
        this.center = center;
        this.right = right;
    }

    /**
     * Retrieves the left value of the triplet.
     *
     * @return The left value of the triplet.
     */
    public L getLeft() {
        return this.left;
    }

    /**
     * Retrieves the center value of the triplet.
     *
     * @return The center value of the triplet.
     */
    public C getCenter() {
        return this.center;
    }

    /**
     * Retrieves the right value of the triplet.
     *
     * @return The right value of the triplet.
     */
    public R getRight() {
        return this.right;
    }

    /**
     * Creates a new instance of Triplet with the specified left, center, and right values.
     *
     * @param <L>    The type of the left value.
     * @param <C>    The type of the center value.
     * @param <R>    The type of the right value.
     * @param left   The left value of the triplet.
     * @param center The center value of the triplet.
     * @param right  The right value of the triplet.
     * @return A new Triplet instance with the specified left, center, and right values.
     */
    public static <L, C, R> Triplet<L, C, R> of(L left, C center, R right) {
        return new Triplet<>(left, center, right);
    }

    /**
     * Checks whether this Triplet is equal to another object.
     * Two Triplets are considered equal if their corresponding left, center, and right values are equal.
     *
     * @param o The object to compare to this Triplet.
     * @return true if the objects are equal; otherwise, false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
        return Objects.equals(this.left, triplet.left) && Objects.equals(this.center, triplet.center) && Objects.equals(this.right, triplet.right);
    }
}