package com.coffeeshop.service.implementations;

import com.coffeeshop.exception.InvalidDataException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UrlDataFetcherTest {

    @Test
    void fetchData_invalidUrl_throwsInvalidDataException() {
        UrlDataFetcher fetcher = new UrlDataFetcher();

        InvalidDataException ex = assertThrows(InvalidDataException.class,
                () -> fetcher.fetchData("invalid-url"));

        assertTrue(ex.getMessage().contains("Invalid URL format"));
    }

    @Test
    void fetchData_statusNot200_throwsInvalidDataException() throws Exception {
        HttpClient mockClient = Mockito.mock(HttpClient.class);
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(mockResponse.statusCode()).thenReturn(404);
        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        UrlDataFetcher fetcher = new UrlDataFetcher(mockClient);

        InvalidDataException ex = assertThrows(InvalidDataException.class,
                () -> fetcher.fetchData("https://test.com"));

        assertTrue(ex.getMessage().contains("Failed to fetch data from URL."));
        assertTrue(ex.getMessage().contains("404"));
    }

    @Test
    void fetchData_success_returnsBody() throws Exception {
        HttpClient mockClient = Mockito.mock(HttpClient.class);
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockResponse.body()).thenReturn("Starbucks Seattle,47.5809,-122.3160");
        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        UrlDataFetcher fetcher = new UrlDataFetcher(mockClient);

        String result = fetcher.fetchData("https://test.com");

        assertEquals("Starbucks Seattle,47.5809,-122.3160", result);
    }

    @Test
    void fetchData_propagatesIOException() throws Exception {
        HttpClient mockClient = Mockito.mock(HttpClient.class);

        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Network error"));

        UrlDataFetcher fetcher = new UrlDataFetcher(mockClient);

        assertThrows(IOException.class, () -> fetcher.fetchData("https://test.com"));
    }

    @Test
    void fetchData_whenInterruptedExceptionThrown_propagatesInterruptedException() throws Exception {
        HttpClient mockClient = Mockito.mock(HttpClient.class);

        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException("Interrupted"));

        UrlDataFetcher fetcher = new UrlDataFetcher(mockClient);

        assertThrows(InterruptedException.class, () -> fetcher.fetchData("https://test.com"));
    }

}
