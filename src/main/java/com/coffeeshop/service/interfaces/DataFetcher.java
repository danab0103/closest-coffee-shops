package com.coffeeshop.service.interfaces;

import com.coffeeshop.exception.InvalidDataException;

import java.io.IOException;

/**
 * Interface for fetching data from various sources (URLs, files, etc.).
 * Allows different data source implementations.
 */
public interface DataFetcher {
    String fetchData(String source) throws IOException, InterruptedException, InvalidDataException;
}
