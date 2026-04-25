package com.coffeeshop.service.implementations;

import com.coffeeshop.exception.InvalidDataException;
import com.coffeeshop.model.CoffeeShop;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvCoffeeShopParserTest {

    private final CsvCoffeeShopParser parser = new CsvCoffeeShopParser();

    @Test
    void parse_validCsv_returnsCoffeeShops() throws Exception {
        String csv = "Starbucks Seattle,47.5809,-122.3160\nStarbucks SF,37.5209,-122.3340\n";

        List<CoffeeShop> shops = parser.parse(csv);

        assertEquals(2, shops.size());
        assertEquals("Starbucks Seattle", shops.get(0).name());
        assertEquals(47.5809, shops.get(0).x());
        assertEquals(-122.3160, shops.get(0).y());
    }

    @Test
    void parse_ignoresEmptyLines() throws Exception {
        String csv = "\nStarbucks Seattle,47.5809,-122.3160\n   \nStarbucks SF,37.5209,-122.3340\n";

        List<CoffeeShop> shops = parser.parse(csv);

        assertEquals(2, shops.size());
    }

    @Test
    void parse_wrongFieldCount_throws() {
        String csv = "Starbucks SF,37.5209\n";

        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> parser.parse(csv));

        assertTrue(ex.getMessage().contains("Expected 3 fields"));
        assertTrue(ex.getMessage().contains("Line 1"));
    }

    @Test
    void parse_emptyName_throws() {
        String csv = ",47.5809,-122.3160\n";

        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> parser.parse(csv));

        assertTrue(ex.getMessage().contains("name cannot be empty"));
        assertTrue(ex.getMessage().contains("Line 1"));
    }

    @Test
    void parse_invalidCoordinate_throws() {
        String csv = "Starbucks SF,SF,-122.3340\n";

        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> parser.parse(csv));

        assertTrue(ex.getMessage().contains("Invalid X coordinate"));
    }
}