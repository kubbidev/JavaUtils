package com.kubbidev.java.util.duration;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class DurationParser {

    /**
     * A map that associates ChronoUnit values with their corresponding regex patterns for parsing.
     */
    private static final Map<ChronoUnit, String> UNITS_PATTERNS = ImmutableMap.<ChronoUnit, String>builder()
            .put(ChronoUnit.YEARS, "y(?:ear)?s?")
            .put(ChronoUnit.MONTHS, "mo(?:nth)?s?")
            .put(ChronoUnit.WEEKS, "w(?:eek)?s?")
            .put(ChronoUnit.DAYS, "d(?:ay)?s?")
            .put(ChronoUnit.HOURS, "h(?:our|r)?s?")
            .put(ChronoUnit.MINUTES, "m(?:inute|in)?s?")
            .put(ChronoUnit.SECONDS, "s(?:econd|ec)?s?")
            .build();

    /**
     * An array of ChronoUnits used for iterating and parsing duration components.
     */
    private static final ChronoUnit[] UNITS = UNITS_PATTERNS.keySet().toArray(new ChronoUnit[0]);

    /**
     * The regex pattern string used to match and parse duration strings.
     */
    private static final String PATTERN_STRING = UNITS_PATTERNS.values().stream()
            .map(pattern -> "(?:(\\d+)\\s*" + pattern + "[,\\s]*)?")
            .collect(Collectors.joining("", "^\\s*", "$"));

    /**
     * The compiled regex pattern used for matching and extracting duration components.
     */
    private static final Pattern PATTERN = Pattern.compile(PATTERN_STRING, Pattern.CASE_INSENSITIVE);

    private DurationParser() {
        throw new AssertionError("No com.kubbidev.java.util.duration.DurationParser instances for you!");
    }

    /**
     * Parses the input duration string and converts it into a Duration object.
     *
     * @param input The duration string to be parsed.
     * @return The parsed Duration object.
     * @throws IllegalArgumentException If the input string does not match the expected duration format.
     */
    public static @NotNull Duration parseDuration(String input) throws IllegalArgumentException {
        Matcher matcher = PATTERN.matcher(input);
        Duration duration = Duration.ZERO;

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unable to parse duration: " + input);
        }

        for (int i = 0; i < UNITS.length; i++) {
            int g = i + 1;

            ChronoUnit unit = UNITS[i];
            if (matcher.group(g) != null && !matcher.group(g).isEmpty()) {
                int n = Integer.parseInt(matcher.group(g));
                if (n > 0) {
                    duration = duration.plus(unit.getDuration().multipliedBy(n));
                }
            }
        }
        return duration;
    }
}