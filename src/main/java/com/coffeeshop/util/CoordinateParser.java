package com.coffeeshop.util;

import com.coffeeshop.exception.InvalidDataException;

public class CoordinateParser {
    /**
     * Private constructor to prevent instantiation.
     */
    private CoordinateParser() {
        throw new UnsupportedOperationException("Utility class can't be instantiated");
    }

    private static double parse(String value) throws NumberFormatException {
        return Double.parseDouble(value.trim());
    }

    /**
     * Parses a coordinate string for CLI arguments.
     * Throws IllegalArgumentException on invalid input (appropriate for user input).
     */
    public static double parseCoordinate(String value, String coordinateName) {
        try {
            return parse(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid %s coordinate: '%s'. Must be a valid number.",
                            coordinateName, value), e);
        }
    }

    /**
     * Parses a coordinate string from CSV data.
     * Throws InvalidDataException on invalid input (appropriate for data validation).
     */
    public static double parseCoordinateFromCsv(String value, int lineNumber, String coordinateName) throws InvalidDataException {
        try {
            return parse(value);
        } catch (NumberFormatException e) {
            throw new InvalidDataException(String.format("Line %d: Invalid %s coordinate '%s'. Must be a valid number.",
                            lineNumber, coordinateName, value), e);
        }
    }
}
