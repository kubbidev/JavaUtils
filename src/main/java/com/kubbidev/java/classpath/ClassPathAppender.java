package com.kubbidev.java.classpath;

import java.nio.file.Path;

/**
 * Interface which allows access to add URLs to the classpath at runtime.
 */
public interface ClassPathAppender extends AutoCloseable {

    void addJarToClasspath(Path file);

    @Override
    default void close() {

    }
}