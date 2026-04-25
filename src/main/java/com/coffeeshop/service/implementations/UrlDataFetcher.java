package com.coffeeshop.service.implementations;

import com.coffeeshop.exception.InvalidDataException;
import com.coffeeshop.service.interfaces.DataFetcher;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

/**
 * Fetches data from HTTP/HTTPS URLs using Java's HttpClient.
 */
public class UrlDataFetcher implements DataFetcher {

    private final HttpClient httpClient;
    private static final Duration CONNECTION_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    public UrlDataFetcher() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(CONNECTION_TIMEOUT)
                .build();
    }

    public UrlDataFetcher(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String fetchData(String url) throws IOException, InterruptedException, InvalidDataException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(REQUEST_TIMEOUT)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new InvalidDataException(
                        String.format("Failed to fetch data from URL. HTTP Status: %d", response.statusCode())
                );
            }

            return response.body();

        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Invalid URL format: " + url);
        } catch (HttpTimeoutException e) {
            throw new IOException("Request timed out. Server may be unavailable: " + url);
        }
    }
}