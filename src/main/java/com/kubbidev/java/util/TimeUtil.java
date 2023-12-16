package com.kubbidev.java.util;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public final class TimeUtil {

    private TimeUtil() {
        throw new AssertionError("No com.kubbidev.java.util.TimeUtil instances for you!");
    }

    /**
     * @param instant The instant
     * @return The duration elapsed since {@code instant}.
     */
    public static Duration elapsed(@NotNull Instant instant) {
        return Duration.ofMillis(Math.abs(ChronoUnit.MILLIS.between(Instant.now(), instant)));
    }

    /**
     * @param epochMilli The epoch milliseconds.
     * @return The duration elapsed since {@code epochMillis}.
     */
    public static Duration elapsed(long epochMilli) {
        return TimeUtil.elapsed(Instant.ofEpochMilli(epochMilli));
    }

    /**
     * @param instant The instant to create the date-time from.
     * @return {@code instant} converted as a {@link LocalDateTime} using the {@code ZoneId.systemDefault()} time-zone.
     */
    public static LocalDateTime toLocalDateTime(@NotNull Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}