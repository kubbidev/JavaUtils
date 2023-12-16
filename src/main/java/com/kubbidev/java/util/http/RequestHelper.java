package com.kubbidev.java.util.http;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RequestHelper {

    /**
     * User agent string for HTTP requests.
     */
    public static final String USER_AGENT = "kubbidev/1.0.0/javautils (1.0.0)";

    /**
     * The default HttpClient instance.
     */
    public static final HttpClient CLIENT = HttpClient.newHttpClient();

    /**
     * Default headers applied to each request.
     */
    public static final Consumer<HttpHeaders> DEFAULT_HEADERS = entries -> {
        entries.add(HttpHeaderNames.USER_AGENT, USER_AGENT);
    };

    private final URI uri;

    private HttpMethod method = HttpMethod.GET;
    private HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.noBody();
    private Consumer<HttpHeaders> headers = DEFAULT_HEADERS;

    /**
     * Constructs a new RequestHelper instance with the specified URI.
     *
     * @param uri The URI for the HTTP request.
     */
    private RequestHelper(URI uri) {
        this.uri = uri;
    }

    /**
     * Creates a RequestHelper instance with the specified URI.
     *
     * @param uri The URI for the HTTP request.
     * @return A new RequestHelper instance.
     */
    public static RequestHelper fromUri(URI uri) {
        return new RequestHelper(uri);
    }

    /**
     * Creates a RequestHelper instance with the specified URL.
     *
     * @param url The URL for the HTTP request.
     * @return A new RequestHelper instance.
     */
    public static RequestHelper fromUrl(String url) {
        return new RequestHelper(URI.create(url));
    }

    /**
     * Sets the HTTP method for the request.
     *
     * @param method The HTTP method to set.
     * @return This RequestHelper instance for method chaining.
     */
    public @NotNull RequestHelper setMethod(HttpMethod method) {
        this.method = Objects.requireNonNull(method);
        return this;
    }

    /**
     * Sets the request body publisher.
     *
     * @param publisher The request body publisher.
     * @return This RequestHelper instance for method chaining.
     */
    public @NotNull RequestHelper setBodyPublisher(HttpRequest.BodyPublisher publisher) {
        this.bodyPublisher = Objects.requireNonNull(publisher);
        return this;
    }

    /**
     * Adds a header to the request.
     *
     * @param name  The name of the header.
     * @param value The value of the header.
     * @return This RequestHelper instance for method chaining.
     */
    public @NotNull RequestHelper addHeaders(CharSequence name, String value) {
        this.headers = this.headers.andThen(headers -> headers.add(name, value));
        return this;
    }

    /**
     * Resets headers to the default headers.
     *
     * @return This RequestHelper instance for method chaining.
     */
    public @NotNull RequestHelper resetHeaders() {
        this.headers = DEFAULT_HEADERS;
        return this;
    }

    /**
     * Sends a synchronous HTTP request and returns an optional InputStream.
     *
     * @return An optional InputStream containing the response body.
     */
    public @NotNull Optional<InputStream> request() {
        try {
            HttpResponse<InputStream> httpResponse = CLIENT.send(supplyRequest(), HttpResponse.BodyHandlers.ofInputStream());

            int responseCode = httpResponse.statusCode();
            if (responseCode == 200) {
                return Optional.of(httpResponse.body());
            }

        } catch (IOException | InterruptedException ignored) {
        }
        return Optional.empty();
    }

    /**
     * Sends an asynchronous HTTP request and returns a CompletableFuture of InputStream.
     *
     * @return A CompletableFuture of InputStream containing the response body.
     */
    public @NotNull CompletableFuture<InputStream> requestFuture() {
        return CLIENT.sendAsync(supplyRequest(), HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(httpResponse -> {

            int responseCode = httpResponse.statusCode();
            if (responseCode == 200) {
                return httpResponse.body();
            }
            return InputStream.nullInputStream();
        });
    }

    /**
     * Constructs and returns the HttpRequest for the request.
     *
     * @return The HttpRequest instance.
     */
    private @NotNull HttpRequest supplyRequest() {
        HttpHeaders httpHeadersBuilder = HttpHeaders.newHeaders();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(this.uri)
                .method(
                        this.method.name(),
                        this.bodyPublisher
                );

        this.headers.accept(httpHeadersBuilder);

        if (httpHeadersBuilder.containsEntry()) {
            httpHeadersBuilder.forEach(builder::header);
        } else {
            builder.header("Content-Type", "application/json-rpc");
        }
        return builder.build();
    }
}