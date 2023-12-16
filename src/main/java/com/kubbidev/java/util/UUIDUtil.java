package com.kubbidev.java.util;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public final class UUIDUtil {

    private static final String UUID_REGEX = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}";
    private static final String TRIMMED_UUID_REGEX = "[a-f0-9]{12}4[a-f0-9]{3}[89aAbB][a-f0-9]{15}";
    private static final String ADD_UUID_HYPHENS_REGEX = "([a-f0-9]{8})([a-f0-9]{4})(4[a-f0-9]{3})([89aAbB][a-f0-9]{3})([a-f0-9]{12})";

    /**
     * The special "NIL" UUID representing the zero UUID: 00000000-0000-0000-0000-000000000000.
     */
    public static final UUID NIL = new UUID(0, 0);

    /**
     * A predicate that checks if a given string can be parsed into a valid UUID.
     */
    public static final Predicate<String> PREDICATE = s -> parse(s) != null;

    private UUIDUtil() {
        throw new AssertionError("No com.kubbidev.java.util.UUIDUtil instances for you!");
    }

    /**
     * Parses a UUID from a string representation.
     *
     * @param s The string representation of the UUID.
     * @return The parsed UUID, or null if the string is not a valid UUID.
     */
    @Nullable
    public static UUID fromString(String s) {
        try {
            return UUID.fromString(s);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Parses a UUID from a string representation.
     * If the input string does not match the standard UUID format, it tries to parse it as a trimmed UUID.
     * A trimmed UUID is a UUID without hyphens, represented by a 32-character hexadecimal string.
     *
     * @param s The string representation of the UUID.
     * @return The parsed UUID, or null if the string is not a valid UUID.
     */
    @Nullable
    public static UUID parse(String s) {
        UUID uuid = fromString(s);
        if (uuid == null && s.length() == 32) {
            try {
                uuid = new UUID(
                        Long.parseUnsignedLong(s.substring(0, 16), 16),
                        Long.parseUnsignedLong(s.substring(16), 16)
                );
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        return uuid;
    }

    /**
     * Parses a UUID from a trimmed string representation.
     * A trimmed UUID is a UUID without hyphens, represented by a 32-character hexadecimal string.
     * If the input string already contains hyphens, it is assumed to be a standard UUID representation and is parsed as such.
     * Otherwise, the hyphens are added to the string before parsing it as a standard UUID.
     *
     * @param input The trimmed string representation of the UUID.
     * @return The parsed UUID.
     * @throws IllegalArgumentException if the input string is not a valid UUID or trimmed UUID.
     */
    public static UUID fromTrimmed(String input) {
        if (!isUuid(input)) {
            throw new IllegalArgumentException("Not a UUID");
        } else if (input.contains("-")) {
            return UUID.fromString(input);
        }
        return UUID.fromString(input.replaceAll(ADD_UUID_HYPHENS_REGEX, "$1-$2-$3-$4-$5"));
    }

    /**
     * Converts a UUID to a trimmed string representation without hyphens.
     *
     * @param input The UUID to be trimmed.
     * @return The trimmed string representation of the UUID.
     */
    public static String trim(UUID input) {
        return trim(input.toString());
    }

    /**
     * Converts a string representation of a UUID to a trimmed string representation without hyphens.
     *
     * @param input The string representation of the UUID to be trimmed.
     * @return The trimmed string representation of the UUID.
     */
    public static String trim(String input) {
        return input.replace("-", "");
    }

    /**
     * Checks if a given string is a valid UUID or a trimmed UUID.
     * A trimmed UUID is a UUID without hyphens, represented by a 32-character hexadecimal string.
     *
     * @param input The string to be checked.
     * @return true if the string is a valid UUID or a trimmed UUID; otherwise, false.
     */
    public static boolean isUuid(String input) {
        return input.matches(TRIMMED_UUID_REGEX) || input.matches(UUID_REGEX);
    }
}