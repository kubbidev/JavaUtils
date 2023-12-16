package com.kubbidev.java.util;

import com.kubbidev.java.logging.LoggerAdapter;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class RecursiveFolderExplorer {

    private final Consumer<File> action;
    private final Consumer<RuntimeException> exceptionHandling;

    /**
     * Constructs a new "RecursiveFolderExplorer" with the specified action and a PluginLogger for handling exceptions.
     *
     * @param action The action to be performed on each file encountered during exploration.
     * @param logger The PluginLogger to handle exceptions that may occur during the exploration.
     */
    public RecursiveFolderExplorer(Consumer<File> action, LoggerAdapter logger, String errorLogPrefix) {
        this.action = action;
        this.exceptionHandling = exception -> logger.warn(errorLogPrefix + ": " + exception.getMessage());
    }

    /**
     * Constructs a new "RecursiveFolderExplorer" with the specified action and custom exception handling.
     *
     * @param action            The action to be performed on each file encountered during exploration.
     * @param exceptionHandling The consumer for handling RuntimeExceptions that may occur during the exploration.
     */
    public RecursiveFolderExplorer(Consumer<File> action, Consumer<RuntimeException> exceptionHandling) {
        this.action = action;
        this.exceptionHandling = exceptionHandling;
    }

    /**
     * Recursively explores the specified file and its subdirectories.
     * For each file encountered, the specified action is performed.
     * If the action throws a RuntimeException, the specified exception handling is applied.
     *
     * @param file The root file or folder from which exploration starts.
     */
    public void explore(File file) {
        if (file.isDirectory())
            Arrays.asList(Objects.requireNonNull(file.listFiles())).forEach(this::explore);
        else
            try {
                this.action.accept(file);
            } catch (RuntimeException exception) {
                this.exceptionHandling.accept(exception);
            }
    }
}