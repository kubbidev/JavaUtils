package com.kubbidev.java.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaLoggerAdapter implements LoggerAdapter {
    private final Logger logger;

    public JavaLoggerAdapter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String s, Object... args) {
        this.logger.log(Level.INFO, s, args);
    }

    @Override
    public void warn(String s, Object... args) {
        this.logger.log(Level.WARNING, s, args);
    }

    @Override
    public void warn(String s, Throwable t) {
        this.logger.log(Level.WARNING, s, t);
    }

    @Override
    public void severe(String s, Object... args) {
        this.logger.log(Level.SEVERE, s, args);
    }

    @Override
    public void severe(String s, Throwable t) {
        this.logger.log(Level.SEVERE, s, t);
    }
}