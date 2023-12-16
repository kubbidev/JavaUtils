package com.kubbidev.java.util.placeholder;

import java.util.HashMap;
import java.util.Map;

public class Placeholder {

    private final Closure closure;
    private final Map<String, String> placeholders = new HashMap<>();

    /**
     * Constructs a new FormulaPlaceholder instance with the specified closure character.
     *
     * @param closure The closure type used for defining placeholders.
     */
    public Placeholder(Closure closure) {
        this.closure = closure;
    }

    /**
     * Registers a placeholder with its corresponding value.
     *
     * @param path The path (placeholder name) to be replaced in the formula.
     * @param obj  The object representing the value to replace the placeholder.
     */
    public Placeholder register(String path, Object obj) {
        this.placeholders.put(path, obj.toString());
        return this;
    }

    /**
     * Applies the registered placeholders to the given formula string.
     * Replaces the placeholders in the formula with their corresponding registered values.
     *
     * @param str The formula string containing placeholders.
     * @return The updated formula string with placeholders replaced by their values.
     */
    public String apply(String str) {
        for (Map.Entry<String, String> entry : this.placeholders.entrySet()) {

            String field = entry.getKey();
            String object = entry.getValue();

            str = str.replace(this.closure.getHead() + field + this.closure.getTail(), object);
        }
        return str;
    }

    /**
     * An enum representing different types of closure characters used for defining placeholders.
     * Each closure type has a head and tail character, and placeholders are enclosed between these characters.
     * For example, if the head is '{' and the tail is '}', a placeholder will look like "{placeholder}".
     */
    public enum Closure {
        /**
         * Represents a closure using curly brackets.
         * Placeholders enclosed with curly brackets will look like "{placeholder}".
         */
        BRACKET('{', '}'),

        /**
         * Represents a closure using percentage signs.
         * Placeholders enclosed with percentage signs will look like "%placeholder%".
         */
        PERCENT('%', '%'),

        /**
         * Represents a closure using hashtags.
         * Placeholders enclosed with hashtags will look like "#placeholder#".
         */
        HASHTAG('#', '#');

        private final char head;
        private final char tail;

        /**
         * Constructor for the Closure enum.
         *
         * @param head The opening character of the closure.
         * @param tail The closing character of the closure.
         */
        Closure(char head, char tail) {
            this.head = head;
            this.tail = tail;
        }

        /**
         * Get the opening character of the closure.
         *
         * @return The opening character of the closure.
         */
        public char getHead() {
            return this.head;
        }

        /**
         * Get the closing character of the closure.
         *
         * @return The closing character of the closure.
         */
        public char getTail() {
            return this.tail;
        }
    }
}