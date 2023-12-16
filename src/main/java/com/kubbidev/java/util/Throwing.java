package com.kubbidev.java.util;

public interface Throwing {

    /**
     * Functional interface that represents a runnable task that can throw checked exceptions.
     * It is similar to the standard Runnable interface, but it allows the run() method to throw checked exceptions.
     */
    @FunctionalInterface
    interface Runnable {
        /**
         * Executes the task defined in the run() method.
         *
         * @throws Exception If an exception occurs while executing the task.
         */
        void run() throws Exception;
    }

    /**
     * Functional interface that represents a consumer that can accept a single argument and can throw checked exceptions.
     * It is similar to the standard Consumer interface, but it allows the accept(T t) method to throw checked exceptions.
     *
     * @param <T> The type of the argument accepted by the consumer.
     */
    @FunctionalInterface
    interface Consumer<T> {
        /**
         * Performs the operation on the given argument.
         *
         * @param t The argument to be consumed.
         * @throws Exception If an exception occurs while performing the operation on the argument.
         */
        void accept(T t) throws Exception;
    }
}