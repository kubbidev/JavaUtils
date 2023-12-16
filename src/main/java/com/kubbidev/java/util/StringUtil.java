package com.kubbidev.java.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class StringUtil {

    private static final Pattern SPACES_PATTERN = Pattern.compile(" +");

    /**
     * Represents a failed index search.
     */
    public static final int INDEX_NOT_FOUND = -1;

    private StringUtil() {
        throw new AssertionError("No com.kubbidev.java.util.StringUtil instances for you!");
    }

    /**
     * Removes extra spaces from the input string and replaces consecutive spaces with a single space.
     *
     * @param str The input string.
     * @return The input string with extra spaces removed.
     */
    public static String oneSpace(String str) {
        return str.trim().replaceAll("\\s+", " ");
    }

    /**
     * Removes all spaces from the input string.
     *
     * @param str The input string.
     * @return The input string with all spaces removed.
     */
    public static String noSpace(String str) {
        return str.trim().replaceAll("\\s+", "");
    }

    /**
     * Returns the given string if it is nonempty; {@code null} otherwise.
     *
     * @param string the string to test and possibly return
     * @return {@code string} itself if it is nonempty; {@code null} if it is empty or null
     */
    @Nullable
    public static String emptyToNull(@Nullable String string) {
        return isNullOrEmpty(string) ? null : string;
    }

    /**
     * Returns the given string if it is non-null; the empty string otherwise.
     *
     * @param string the string to test and possibly return
     * @return {@code string} itself if it is non-null; {@code ""} if it is null
     */
    public static String nullToEmpty(@Nullable String string) {
        return (string == null) ? "" : string;
    }

    /**
     * Returns {@code true} if the given string is null or is the empty string.
     *
     * <p>Consider normalizing your string references with {@link #nullToEmpty}. If you do, you can
     * use {@link String#isEmpty()} instead of this method, and you won't need special null-safe forms
     * of methods like {@link String#toUpperCase} either. Or, if you'd like to normalize "in the other
     * direction," converting empty strings to {@code null}, you can use {@link #emptyToNull}.
     *
     * @param string a string reference to check
     * @return {@code true} if the string is null or is the empty string
     */
    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Converts the input string to enum case format.
     * Enum case format converts the string to uppercase, replaces hyphens with underscores, and removes extra spaces.
     *
     * @param str The input string.
     * @return The input string in enum case format.
     */
    public static String enumCase(String str) {
        return str.toUpperCase(Locale.ROOT)
                .replace("-", "_")
                .replace(" ", "_");
    }

    /**
     * Converts the input string to file case format.
     * File case format converts the string to lowercase, replaces underscores with hyphens, and removes extra spaces.
     *
     * @param str The input string.
     * @return The input string in file case format.
     */
    public static String fileCase(String str) {
        return str.toLowerCase(Locale.ROOT)
                .replace("_", "-")
                .replace(" ", "-");
    }

    /**
     * Converts the input string to registry case format.
     * Registry case format is the combination of enum case format and lowercase.
     *
     * @param str The input string.
     * @return The input string in registry case format.
     */
    public static String registryCase(String str) {
        return enumCase(str).toLowerCase(Locale.ROOT);
    }

    /**
     * @param str The string to check, may be {@code null}.
     * @return Whether the string is null or blank.
     */
    public static boolean isBlank(@Nullable String str) {
        return str == null || str.isBlank();
    }

    /**
     * Converts the input string to a friendly name format.
     * A friendly name format capitalizes the first letter of each word and separates words with a space.
     *
     * @param message The input string.
     * @return The input string in friendly name format.
     */
    public static String friendlyName(String message) {
        StringJoiner joiner = new StringJoiner(" ");
        for (String t : message.split("_")) {
            joiner.add(t.toUpperCase(Locale.ROOT).charAt(0) + t.substring(1).toLowerCase(Locale.ROOT));
        }
        return joiner.toString();
    }

    /**
     * @param str      The string to abbreviate, may be {@code null}.
     * @param maxWidth Maximum length of result string, must be at least 4.
     * @return Abbreviated string, {@code null} if null string input.
     * @throws IllegalArgumentException If the width is too small.
     */
    public static String abbreviate(@Nullable String str, int maxWidth) {
        if (str == null) {
            return null;
        }
        if (str.length() <= maxWidth) {
            return str;
        }
        String abbrevMarker = "...";
        if (str.length() - abbrevMarker.length() <= 0) {
            throw new IllegalArgumentException("Minimum abbreviation width is " + (abbrevMarker.length() + 1));
        }
        return str.substring(0, maxWidth - abbrevMarker.length()) + abbrevMarker;
    }

    /**
     * @param str The string to capitalize, may be {@code null}.
     * @return The capitalized string.
     */
    public static String capitalize(@Nullable String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * The function returns the argument string with whitespace normalized by using {@link String#trim()} to remove
     * leading and trailing whitespace and then replacing sequences of whitespace characters by a single space.
     *
     * @param str The source string to normalize whitespaces from, may be {@code null}.
     * @return The modified string with whitespace normalized or {@code null} if null string input.
     */
    public static String normalizeSpace(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return SPACES_PATTERN.matcher(str.trim()).replaceAll(" ");
    }

    /**
     * @param str      The string from which to remove patterns, may be {@code null}.
     * @param toRemove The strings to be substituted for each match.
     * @return The resulting string.
     */
    public static String remove(@Nullable String str, @NotNull String... toRemove) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(Arrays.stream(toRemove)
                .filter(replacement -> !replacement.isEmpty())
                .map(Pattern::quote)
                .collect(Collectors.joining("|")), "");
    }

    /**
     * Converts a hexadecimal color string to an array of RGB values.
     *
     * @param colorStr The hexadecimal color string (e.g., "#FF0000").
     * @return An array of three integers representing the RGB values (red, green, and blue).
     */
    public static int[] hexToRGB(String colorStr) {
        return new int[]{
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16)};
    }

    /**
     * Capitalizes the first letter of the input string and converts the rest of the letters to lowercase.
     *
     * @param original The input string.
     * @return The input string with the first letter capitalized and the rest of the letters in lowercase.
     */
    public static String capitalizeFirstLetter(String original) {
        if (original.isEmpty())
            return original;
        return original.substring(0, 1).toUpperCase(Locale.ROOT) + original.substring(1).toLowerCase(Locale.ROOT);
    }

    /**
     * <p>Gets the String that is nested in between two instances of the
     * same String.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.
     * A <code>null</code> tag returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtils.substringBetween(null, *)            = null
     * StringUtils.substringBetween("", "")             = ""
     * StringUtils.substringBetween("", "tag")          = null
     * StringUtils.substringBetween("tagabctag", null)  = null
     * StringUtils.substringBetween("tagabctag", "")    = ""
     * StringUtils.substringBetween("tagabctag", "tag") = "abc"
     * </pre>
     *
     * @param str  the String containing the substring, may be null
     * @param tag  the String before and after the substring, may be null
     * @return the substring, <code>null</code> if no match
     */
    public static String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag);
    }

    /**
     * <p>Gets the String that is nested in between two Strings.
     * Only the first match is returned.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.
     * A <code>null</code> open/close returns <code>null</code> (no match).
     * An empty ("") open and close returns an empty string.</p>
     *
     * <pre>
     * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringUtils.substringBetween(null, *, *)          = null
     * StringUtils.substringBetween(*, null, *)          = null
     * StringUtils.substringBetween(*, *, null)          = null
     * StringUtils.substringBetween("", "", "")          = ""
     * StringUtils.substringBetween("", "", "]")         = null
     * StringUtils.substringBetween("", "[", "]")        = null
     * StringUtils.substringBetween("yabcz", "", "")     = ""
     * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param str  the String containing the substring, may be null
     * @param open  the String before the substring, may be null
     * @param close  the String after the substring, may be null
     * @return the substring, <code>null</code> if no match
     */
    public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != INDEX_NOT_FOUND) {
            int end = str.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * Retrieves a list of strings from the source list that start with the specified argument.
     *
     * @param arg    The argument to match.
     * @param source The source list of strings.
     * @return A list of strings from the source list that match the specified argument.
     */
    public static List<String> getByFirstLetters(String arg, List<String> source) {
        List<String> ret = new ArrayList<>();
        List<String> sugg = new ArrayList<>(source);

        copyPartialMatches(arg, sugg, ret);
        Collections.sort(ret);
        return ret;
    }

    /**
     * Formats a time delay in seconds to the format "hh:mm:ss".
     *
     * @param epochSecond The time delay in seconds.
     * @return The formatted time delay in the format "hh:mm:ss".
     */
    public static String parseDelay(long epochSecond) {
        long hours = epochSecond / 3600;
        long minutes = (epochSecond % 3600) / 60;
        long seconds = epochSecond % 60;

        StringBuilder sb = new StringBuilder(8);
        if (hours < 10) {
            sb.append('0');
        }
        sb.append(hours).append(':');
        if (minutes < 10) {
            sb.append('0');
        }
        sb.append(minutes).append(':');
        if (seconds < 10) {
            sb.append('0');
        }
        sb.append(seconds);
        return sb.toString();
    }

    /**
     * @param str The string to split, may be {@code null}.
     * @return A list without limits containing all the elements resulting of {@code str} splitted using space excluding
     * empty results.
     */
    public static List<String> split(@Nullable String str) {
        return StringUtil.split(str, -1);
    }

    /**
     * @param str   The string to split, may be {@code null}.
     * @param limit The result threshold.
     * @return An endless list containing all the elements resulting of {@code str} splitted using space excluding
     * empty results.
     */
    public static List<String> split(@Nullable String str, int limit) {
        return StringUtil.split(str, limit, " ");
    }

    /**
     * @param str       The string to split, may be {@code null}.
     * @param limit     The result threshold.
     * @param delimiter The delimiting regular expression.
     * @return A list with a maximum number of {@code limit} elements containing all the results of {@code str} splitted
     * using {@code delimiter} excluding empty results.
     */
    public static List<String> split(@Nullable String str, int limit, @NotNull String delimiter) {
        if (str == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(str.split(delimiter, limit))
                .map(String::trim)
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * @param str       The string to split, may be {@code null}.
     * @param delimiter The delimiting regular expression.
     * @return An endless list all the elements resulting of {@code str} splitted using {@code delimiter} excluding
     * empty results.
     */
    public static List<String> split(@Nullable String str, @NotNull String delimiter) {
        return StringUtil.split(str, -1, delimiter);
    }

    /**
     * Converts a collection of strings into a single string, separated by spaces and newlines.
     *
     * @param list The collection of strings.
     * @return The concatenated string with each element separated by a space and a newline.
     */
    public static String convertListToString(Collection<String> list) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String string : list) {
            sb.append(i > 0 ? '\n' + string : string);
            i++;
        }
        return sb.toString();
    }

    /**
     * Decodes a hexadecimal string into a byte array.
     *
     * @param hexString The hexadecimal string to be decoded.
     * @return The byte array containing the decoded bytes.
     * @throws IllegalArgumentException If the hexadecimal string is invalid (has an odd length).
     */
    public static byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException("Invalid hexadecimal String supplied.");
        }
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    /**
     * Converts a two-character hexadecimal string to a byte value.
     *
     * @param hexString The two-character hexadecimal string (e.g., "FF").
     * @return The byte value represented by the hexadecimal string.
     */
    public static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    /**
     * Converts a hexadecimal character to its integer value (0-15).
     *
     * @param hexChar The hexadecimal character.
     * @return The integer value represented by the hexadecimal character.
     * @throws IllegalArgumentException If the input character is not a valid hexadecimal character.
     */
    public static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if (digit == -1) {
            throw new IllegalArgumentException("Invalid Hexadecimal Character: " + hexChar);
        }
        return digit;
    }

    /**
     * Copies strings from the "originals" iterable to the "collection" collection if they start with the specified "token".
     *
     * @param <T>        The type of the collection.
     * @param token      The token to match against the strings in the "originals" iterable.
     * @param originals  The iterable containing the original strings to check.
     * @param collection The collection to which the matching strings will be copied.
     * @return The "collection" collection with the added matching strings.
     * @throws UnsupportedOperationException If the operation is not supported by the collection.
     * @throws IllegalArgumentException      If "originals" or "collection" is null.
     */
    public static <T extends Collection<? super String>> T copyPartialMatches(String token,
                                                                              Iterable<String> originals,
                                                                              T collection) throws UnsupportedOperationException, IllegalArgumentException {
        for (String string : originals) {
            if (startsWithIgnoreCase(string, token)) {
                collection.add(string);
            }
        }
        return collection;
    }

    /**
     * Checks if a string starts with a specified prefix (case-insensitive).
     *
     * @param string The string to check.
     * @param prefix The prefix to compare with the start of the string.
     * @return true if the string starts with the specified prefix (case-insensitive); otherwise, false.
     * @throws IllegalArgumentException If "string" or "prefix" is null.
     * @throws NullPointerException     If "string" is an empty string.
     */
    public static boolean startsWithIgnoreCase(String string, String prefix) throws IllegalArgumentException, NullPointerException {
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}