package com.coffeeshop.util;

import com.coffeeshop.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateParserTest {
    @Test
    void parseCoordinate_validNumber_returnsDouble() {
        assertEquals(12.5, CoordinateParser.parseCoordinate("12.5", "X"));
        assertEquals(-3.0, CoordinateParser.parseCoordinate("-3", "Y"));
    }

    @Test
    void parseCoordinate_invalid_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> CoordinateParser.parseCoordinate("abc", "X"));
        assertTrue(ex.getMessage().contains("Invalid X coordinate"));
    }

    @Test
    void parseCoordinateFromCsv_invalid_throwsInvalidDataException() {
        InvalidDataException ex = assertThrows(InvalidDataException.class,
                () -> CoordinateParser.parseCoordinateFromCsv("abc", 2, "Y"));
        assertTrue(ex.getMessage().contains("Line 2"));
        assertTrue(ex.getMessage().contains("Invalid Y coordinate"));
    }
}