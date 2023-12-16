package com.kubbidev.java.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("CallToPrintStackTrace")
public final class Iterators {

    private Iterators() {
        throw new AssertionError("No com.kubbidev.java.util.Iterators instances for you!");
    }

    /**
     * Iterates over the elements of an Iterable, applying the specified action to each element.
     * If an exception occurs during the iteration or the action, the exception is printed to the standard error output,
     * and the method returns false. Otherwise, it returns true.
     *
     * @param <E>    The type of elements in the Iterable.
     * @param iterable The Iterable to iterate over.
     * @param action   The action to apply to each element.
     * @return true if the iteration is successful without any exceptions, false otherwise.
     */
    public static <E> boolean tryIterate(Iterable<E> iterable, Throwing.Consumer<E> action) {
        boolean success = true;
        for (E element : iterable) {
            try {
                action.accept(element);
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }

    /**
     * Iterates over the elements of an Iterable, applying the specified mapping function to each element,
     * and then applying the action to the mapped result.
     * If an exception occurs during the iteration, mapping, or the action, the exception is printed to the standard error output,
     * and the method returns false. Otherwise, it returns true.
     *
     * @param <I>     The type of elements in the input Iterable.
     * @param <O>     The type of elements in the output after mapping.
     * @param iterable The Iterable to iterate over.
     * @param mapping  The mapping function to apply to each element.
     * @param action   The action to apply to the mapped result of each element.
     * @return true if the iteration is successful without any exceptions, false otherwise.
     */
    public static <I, O> boolean tryIterate(Iterable<I> iterable, Function<I, O> mapping, Consumer<O> action) {
        boolean success = true;
        for (I element : iterable) {
            try {
                action.accept(mapping.apply(element));
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }

    /**
     * Iterates over the elements of an array, applying the specified action to each element.
     * If an exception occurs during the iteration or the action, the exception is printed to the standard error output,
     * and the method returns false. Otherwise, it returns true.
     *
     * @param <E>   The type of elements in the array.
     * @param array The array to iterate over.
     * @param action The action to apply to each element.
     * @return true if the iteration is successful without any exceptions, false otherwise.
     */
    public static <E> boolean tryIterate(E[] array, Consumer<E> action) {
        boolean success = true;
        for (E element : array) {
            try {
                action.accept(element);
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }

    /**
     * Iterates over the elements of an array, applying the specified mapping function to each element,
     * and then applying the action to the mapped result.
     * If an exception occurs during the iteration, mapping, or the action, the exception is printed to the standard error output,
     * and the method returns false. Otherwise, it returns true.
     *
     * @param <I>     The type of elements in the input array.
     * @param <O>     The type of elements in the output after mapping.
     * @param array   The array to iterate over.
     * @param mapping The mapping function to apply to each element.
     * @param action  The action to apply to the mapped result of each element.
     * @return true if the iteration is successful without any exceptions, false otherwise.
     */
    public static <I, O> boolean tryIterate(I[] array, Function<I, O> mapping, Consumer<O> action) {
        boolean success = true;
        for (I element : array) {
            try {
                action.accept(mapping.apply(element));
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }

    /**
     * Divides an Iterable into sublists of a specified size.
     *
     * @param <E>    The type of elements in the Iterable.
     * @param source The Iterable to be divided.
     * @param size   The maximum size of each sublist.
     * @return A List of Lists containing sublists of the specified size from the original Iterable.
     */
    public static <E> List<List<E>> divideIterable(Iterable<E> source, int size) {

        List<List<E>> lists = new ArrayList<>();
        Iterator<E> it = source.iterator();

        while (it.hasNext()) {
            List<E> subList = new ArrayList<>();
            for (int i = 0; it.hasNext() && i < size; i++) {
                subList.add(it.next());
            }
            lists.add(subList);
        }
        return lists;
    }
}