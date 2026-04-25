package com.coffeeshop.exception;

/**
 * Custom exception thrown when CSV data is malformed or invalid.
 */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}