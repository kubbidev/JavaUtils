package com.kubbidev.java.logging;

import org.slf4j.Logger;

public class Slf4JLoggerAdapter implements LoggerAdapter {
    private final Logger logger;

    public Slf4JLoggerAdapter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String s, Object... args) {
        this.logger.info(s, args);
    }

    @Override
    public void warn(String s, Object... args) {
        this.logger.warn(s, args);
    }

    @Override
    public void warn(String s, Throwable t) {
        this.logger.warn(s, t);
    }

    @Override
    public void severe(String s, Object... args) {
        this.logger.error(s, args);
    }

    @Override
    public void severe(String s, Throwable t) {
        this.logger.error(s, t);
    }
}