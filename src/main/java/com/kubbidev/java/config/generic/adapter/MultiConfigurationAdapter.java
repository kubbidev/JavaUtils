package com.kubbidev.java.config.generic.adapter;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * A {@link ConfigurationAdapter} composed of one or more other ConfigurationAdapters.
 */
public class MultiConfigurationAdapter implements ConfigurationAdapter {

    private final List<ConfigurationAdapter> adapters;

    /**
     * Creates a {@link MultiConfigurationAdapter}.
     *
     * <p>The first adapter in the list has priority (the final say) in deciding what the value is.
     * All adapters are tried in reverse order, and the value returned from the previous adapter
     * is passed into the next as the {@code def} value.</p>
     *
     * @param adapters a list of adapters
     */
    public MultiConfigurationAdapter(List<ConfigurationAdapter> adapters) {
        this.adapters = ImmutableList.copyOf(adapters).reverse();
    }

    public MultiConfigurationAdapter(ConfigurationAdapter... adapters) {
        this(ImmutableList.copyOf(adapters));
    }

    @Override
    public void reload() {
        for (ConfigurationAdapter adapter : this.adapters) {
            adapter.reload();
        }
    }

    @Override
    public @NotNull String getString(String path, String def) {
        String result = def;
        for (ConfigurationAdapter adapter : this.adapters) {
            result = adapter.getString(path, result);
        }
        return result;
    }

    @Override
    public int getInteger(String path, int def) {
        int result = def;
        for (ConfigurationAdapter adapter : this.adapters) {
            result = adapter.getInteger(path, result);
        }
        return result;
    }

    @Override
    public long getLong(String path, long def) {
        long result = def;
        for (ConfigurationAdapter adapter : this.adapters) {
            result = adapter.getLong(path, result);
        }
        return result;
    }

    @Override
    public double getDouble(String path, double def) {
        double result = def;
        for (ConfigurationAdapter adapter : this.adapters) {
            result = adapter.getDouble(path, result);
        }
        return result;
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        boolean result = def;
        for (ConfigurationAdapter adapter : this.adapters) {
            result = adapter.getBoolean(path, result);
        }
        return result;
    }

    @Override
    public @NotNull List<String> getStringList(String path, List<String> def) {
        List<String> result = def;
        for (ConfigurationAdapter adapter : this.adapters) {
            result = adapter.getStringList(path, result);
        }
        return result;
    }

    @Override
    public @NotNull Map<String, String> getStringMap(String path, Map<String, String> def) {
        Map<String, String> result = def;
        for (ConfigurationAdapter adapter : this.adapters) {
            result = adapter.getStringMap(path, result);
        }
        return result;
    }

    @Override
    public <E extends Enum<E>> @NotNull E getEnum(String path, E def) {
        E result = def;
        for (ConfigurationAdapter adapter : this.adapters) {
            result = adapter.getEnum(path, result);
        }
        return result;
    }
}
