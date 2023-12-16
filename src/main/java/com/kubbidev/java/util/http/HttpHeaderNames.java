package com.kubbidev.java.util.http;

/**
 * Standard HTTP header names.
 * <p>
 * These are all defined as lowercase to support HTTP/2 requirements while also not
 * violating HTTP/1.x requirements.  New header names should always be lowercase.
 */
public final class HttpHeaderNames {

    /**
     * {@code "user-agent"}
     */
    public static final String USER_AGENT = "user-agent";
    /**
     * {@code "authorization"}
     */
    public static final String AUTHORIZATION = "authorization";

    private HttpHeaderNames() {
        throw new AssertionError("No com.kubbidev.java.util.http.HttpHeaderNames instances for you!");
    }
}