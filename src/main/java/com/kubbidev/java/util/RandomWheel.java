package com.kubbidev.java.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWheel<T> {

    private record RandomWheelSection<T>(double weight, T value) { }

    private final List<RandomWheelSection<T>> sections = new ArrayList<>();
    private final Random random = new Random();

    private double totalWeight = 0;

    /**
     * Adds a new section to the random wheel with the given weight and value.
     * The weight determines the probability of this section being selected.
     * A higher weight increases the chances of the corresponding value being picked.
     *
     * @param weight The weight of the section.
     * @param value  The value associated with this section.
     */
    public void addWheelSection(double weight, T value) {
        this.sections.add(new RandomWheelSection<>(weight, value));
        this.totalWeight += weight;
    }

    /**
     * Clears all sections from the random wheel.
     * After calling this method, the wheel will have no sections to choose from.
     */
    public void clearSection() {
        this.sections.clear();
        this.totalWeight = 0;
    }

    /**
     * Randomly selects a value from the random wheel based on the weights of its sections.
     * The higher the weight of a section, the more likely its associated value will be selected.
     *
     * @return A value randomly selected from the random wheel based on the weights of its sections.
     */
    public T draw() {
        double rnd = this.totalWeight * this.random.nextDouble();
        double sum = 0;

        for (RandomWheelSection<T> section : this.sections) {
            sum += section.weight;

            if (sum >= rnd)
                return section.value;
        }
        // If no section is selected before the loop finishes, return the last section's value.
        return this.sections.get(this.sections.size() - 1).value;
    }
}