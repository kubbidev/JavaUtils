package com.kubbidev.java.logging;

/**
 * Represents the logger instance being used on the platform.
 *
 * <p>Messages sent using the logger are sent prefixed with a tag,
 * and on some implementations will be colored depending on the message type.</p>
 */
public interface LoggerAdapter {

    void info(String s, Object... args);

    void warn(String s, Object... args);

    void warn(String s, Throwable t);

    void severe(String s, Object... args);

    void severe(String s, Throwable t);
}
