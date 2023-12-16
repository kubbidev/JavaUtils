package com.kubbidev.java.config.generic.adapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface ConfigurationAdapter {

    void reload();

    @NotNull String getString(String path, String def);

    int getInteger(String path, int def);

    long getLong(String path, long def);

    double getDouble(String path, double def);

    boolean getBoolean(String path, boolean def);

    @NotNull List<String> getStringList(String path, List<String> def);

    @NotNull Map<String, String> getStringMap(String path, Map<String, String> def);

    @NotNull <E extends Enum<E>> E getEnum(String path, E def);
}