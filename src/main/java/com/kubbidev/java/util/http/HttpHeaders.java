package com.kubbidev.java.util.http;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

public final class HttpHeaders implements Iterable<Map.Entry<String, String>> {

    private final Map<String, String> headers = new HashMap<>();

    /**
     * Creates an {@code HttpHeaders}.
     *
     * @return a new http headers
     */
    public static HttpHeaders newHeaders() {
        return new HttpHeaders();
    }

    /**
     * @see #get(CharSequence)
     */
    public String get(String name) {
        return headers.get(name);
    }

    public void forEach(BiConsumer<String, String> action) {
        requireNonNull(action);
        for (Map.Entry<String, String> entry : this.headers.entrySet()) {
            String k;
            String v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    }

    /**
     * Returns the value of a header with the specified name.  If there are
     * more than one values for the specified name, the first value is returned.
     *
     * @param name The name of the header to search
     * @return The first header value or {@code null} if there is no such header
     */
    public String get(CharSequence name) {
        return get(name.toString());
    }

    /**
     * Returns the value of a header with the specified name.  If there are
     * more than one values for the specified name, the first value is returned.
     *
     * @param name The name of the header to search
     * @return The first header value or {@code defaultValue} if there is no such header
     */
    public String get(CharSequence name, String defaultValue) {
        String value = get(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public Collection<String> getAll() {
        return this.headers.values();
    }

    /**
     * @see #contains(CharSequence)
     */
    public boolean contains(String name) {
        return this.headers.containsKey(name);
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return this.headers.entrySet().iterator();
    }

    /**
     * Checks to see if there is a header with the specified name
     *
     * @param name The name of the header to search for
     * @return True if at least one header is found
     */
    public boolean contains(CharSequence name) {
        return contains(name.toString());
    }

    /**
     * Checks if no header exists.
     */
    public boolean isEmpty() {
        return this.headers.isEmpty();
    }

    /**
     * Checks if at least one header exists.
     */
    public boolean containsEntry() {
        return !isEmpty();
    }

    /**
     * Returns the number of headers in this object.
     */
    public int size() {
        return this.headers.size();
    }

    /**
     * Returns a new {@link Set} that contains the names of all headers in this object.  Note that modifying the
     * returned {@link Set} will not affect the state of this object.  If you intend to enumerate over the header
     * entries only, use {@link #iterator()} instead, which has much less overhead.
     */
    public Set<String> names() {
        return this.headers.keySet();
    }

    /**
     * @see #add(CharSequence, String)
     */
    public HttpHeaders add(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    /**
     * Adds a new header with the specified name and value.
     * <p>
     * If the specified value is not a {@link String}, it is converted
     * into a {@link String} by {@link Object#toString()}, except in the cases
     * of {@link Date} and {@link Calendar}, which are formatted to the date
     * format defined in <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1">RFC2616</a>.
     *
     * @param name  The name of the header being added
     * @param value The value of the header being added
     * @return {@code this}
     */
    public HttpHeaders add(CharSequence name, String value) {
        return add(name.toString(), value);
    }

    /**
     * @see #add(CharSequence, Iterable)
     */
    public HttpHeaders add(String name, Iterable<String> values) {
        for (String value : values) {
            if (value == null) {
                break;
            }

            add(name, value);
        }
        return this;
    }

    /**
     * Adds a new header with the specified name and values.
     * <p>
     * This getMethod can be represented approximately as the following code:
     * <pre>
     * for (String v: values) {
     *     if (v == null) {
     *         break;
     *     }
     *     headers.add(name, v);
     * }
     * </pre>
     *
     * @param name   The name of the headers being set
     * @param values The values of the headers being set
     * @return {@code this}
     */
    public HttpHeaders add(CharSequence name, Iterable<String> values) {
        return add(name.toString(), values);
    }

    /**
     * Adds all header entries of the specified {@code headers}.
     *
     * @return {@code this}
     */
    public HttpHeaders add(HttpHeaders headers) {
        requireNonNull(headers, "headers");
        for (Map.Entry<String, String> e : headers) {
            add(e.getKey(), e.getValue());
        }
        return this;
    }

    /**
     * @see #remove(CharSequence)
     */
    public HttpHeaders remove(String name) {
        this.headers.remove(name);
        return this;
    }

    /**
     * Removes the header with the specified name.
     *
     * @param name The name of the header to remove
     * @return {@code this}
     */
    public HttpHeaders remove(CharSequence name) {
        return remove(name.toString());
    }

    /**
     * Removes all headers.
     *
     * @return {@code this}
     */
    public HttpHeaders clear() {
        this.headers.clear();
        return this;
    }
}