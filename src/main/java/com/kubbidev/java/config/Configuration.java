package com.kubbidev.java.config;

import com.kubbidev.java.config.generic.KeyedConfiguration;
import com.kubbidev.java.config.generic.key.ConfigKey;
import com.kubbidev.java.config.generic.adapter.ConfigurationAdapter;

import java.util.List;

public class Configuration extends KeyedConfiguration {

    public Configuration(ConfigurationAdapter adapter, List<? extends ConfigKey<?>> keys) {
        super(adapter, keys);
        // init the config by loading all keys and assigned them
        // to their corresponding values
        init();
    }
}