package com.kubbidev.java.config.generic.adapter;

import com.kubbidev.java.logging.LoggerAdapter;
import org.jetbrains.annotations.Nullable;

public class SystemPropertyConfigAdapter extends StringBasedConfigurationAdapter {
    private static final String PREFIX = "kubbidev.";

    private final LoggerAdapter logger;

    public SystemPropertyConfigAdapter(LoggerAdapter logger) {
        this.logger = logger;
    }

    @Override
    public void reload() {
        // no-op
    }

    @Override
    protected @Nullable String resolveValue(String path) {
        // e.g.
        // 'server'            -> kubbidev.server
        // 'data.table_prefix' -> kubbidev.data.table-prefix
        String key = PREFIX + path;

        String value = System.getProperty(key);
        if (value != null) {
            this.logger.info("Resolved configuration value from system property: " + key + " = " + (path.contains("password") ? "*****" : value));
        }
        return value;
    }
}
