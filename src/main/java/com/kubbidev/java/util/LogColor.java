package com.kubbidev.java.util;

import org.jetbrains.annotations.NotNull;

/**
 * The "LogColor" enum represents ANSI color codes used for formatting console log messages with colors and styles.
 * <p>
 * It is used to apply different text colors and styles to log messages when printing to the console.
 */
public enum LogColor {

    BLACK("\033[30m"),              // Black color
    RED("\033[31m"),                // Red color
    GREEN("\033[32m"),              // Green color
    YELLOW("\033[33m"),             // Yellow color
    BLUE("\033[34m"),               // Blue color
    PURPLE("\033[35m"),             // Purple (magenta) color
    CYAN("\033[36m"),               // Cyan color
    WHITE("\033[37m"),              // White color
    BRIGHT_GREEN("\033[38;5;46m"),  // Bright green color
    RESET("\033[0m"),               // Reset all text attributes (no color or style)
    BOLD("\033[1m"),                // Bold text style
    ITALICS("\033[2m"),             // Italic text style
    UNDERLINE("\033[4m");           // Underline text style

    private final String color;

    /**
     * Constructs a new "LogColor" enum constant with the specified ANSI color code.
     *
     * @param color The ANSI color code associated with this log color.
     */
    LogColor(String color) {
        this.color = color;
    }

    /**
     * Returns the ANSI color code associated with this log color.
     *
     * @return The ANSI color code for this log color.
     */
    public @NotNull String color() {
        return this.color;
    }

    @Override
    public String toString() {
        return this.color();
    }
}