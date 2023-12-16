package com.kubbidev.java.config.generic.adapter;

import com.kubbidev.java.logging.LoggerAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class EnvironmentVariableConfigAdapter extends StringBasedConfigurationAdapter {
    private static final String PREFIX = "KUBBIDEV_";

    private final LoggerAdapter logger;

    public EnvironmentVariableConfigAdapter(LoggerAdapter logger) {
        this.logger = logger;
    }

    @Override
    public void reload() {
        // no-op
    }

    @Override
    protected @Nullable String resolveValue(String path) {
        // e.g.
        // 'server'            -> KUBBIDEV_SERVER
        // 'data.table_prefix' -> KUBBIDEV_DATA_TABLE_PREFIX
        String key = PREFIX + path.toUpperCase(Locale.ROOT)
                .replace('-', '_')
                .replace('.', '_');

        String value = System.getenv(key);
        if (value != null) {
            this.logger.info("Resolved configuration value from environment variable: " + key + " = " + (path.contains("password") ? "*****" : value));
        }
        return value;
    }
}
