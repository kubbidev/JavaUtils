package com.kubbidev.java.config.generic.adapter;

import com.google.common.base.Splitter;
import com.kubbidev.java.util.EnumUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class StringBasedConfigurationAdapter implements ConfigurationAdapter {

    private static final Splitter LIST_SPLITTER = Splitter.on(',');
    private static final Splitter.MapSplitter MAP_SPLITTER = Splitter.on(',').withKeyValueSeparator('=');

    protected abstract @Nullable String resolveValue(String path);

    @Override
    public @NotNull String getString(String path, String def) {
        String value = resolveValue(path);
        if (value == null) {
            return def;
        }

        return value;
    }

    @Override
    public int getInteger(String path, int def) {
        String value = resolveValue(path);
        if (value == null) {
            return def;
        }

        try {
            return Integer.parseInt(value);
        } catch (IllegalArgumentException e) {
            return def;
        }
    }

    @Override
    public long getLong(String path, long def) {
        String value = resolveValue(path);
        if (value == null) {
            return def;
        }

        try {
            return Long.parseLong(value);
        } catch (IllegalArgumentException e) {
            return def;
        }
    }

    @Override
    public double getDouble(String path, double def) {
        String value = resolveValue(path);
        if (value == null) {
            return def;
        }

        try {
            return Double.parseDouble(value);
        } catch (IllegalArgumentException e) {
            return def;
        }
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        String value = resolveValue(path);
        if (value == null) {
            return def;
        }

        try {
            return Boolean.parseBoolean(value);
        } catch (IllegalArgumentException e) {
            return def;
        }
    }

    @Override
    public @NotNull List<String> getStringList(String path, List<String> def) {
        String value = resolveValue(path);
        if (value == null) {
            return def;
        }

        return LIST_SPLITTER.splitToList(value);
    }

    @Override
    public @NotNull Map<String, String> getStringMap(String path, Map<String, String> def) {
        String value = resolveValue(path);
        if (value == null) {
            return def;
        }

        return MAP_SPLITTER.split(value);
    }


    @Override
    public @NotNull <E extends Enum<E>> E getEnum(String path, E def) {

        Class<E> clazz = def.getDeclaringClass();
        String value = resolveValue(path);

        if (value == null) {
            return def;
        }
        return Objects.requireNonNull(EnumUtil.getEnum(value, clazz));
    }
}
