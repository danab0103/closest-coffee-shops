package com.coffeeshop.application;

import com.coffeeshop.exception.InvalidDataException;
import com.coffeeshop.service.implementations.CsvCoffeeShopParser;
import com.coffeeshop.service.implementations.EuclideanDistanceCalculator;
import com.coffeeshop.service.interfaces.CoffeeShopParser;
import com.coffeeshop.service.interfaces.DataFetcher;
import com.coffeeshop.service.interfaces.DistanceCalculator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeShopFinderTest {
    @Test
    void findClosest_returnsThreeSortedByDistance() throws Exception {
        DataFetcher fakeFetcher = source -> """
                Starbucks Seattle,47.5809,-122.3160
                Starbucks SF,37.5209,-122.3340
                Starbucks Moscow,55.752047,37.595242
                Starbucks Seattle2,47.5869,-122.3368
                Starbucks Rio De Janeiro,-22.923489,-43.234418
                Starbucks Sydney,-33.871843,151.206767
                """;

        CoffeeShopParser parser = new CsvCoffeeShopParser();
        DistanceCalculator distanceCalculator = new EuclideanDistanceCalculator();
        CoffeeShopFinder finder = new CoffeeShopFinder(fakeFetcher, parser, distanceCalculator);

        List<CoffeeShopDto> result = finder.findClosest(47.6, -122.4, "ignored", 3);

        assertEquals(3, result.size());
        assertEquals("Starbucks Seattle2", result.get(0).name());
        assertEquals(0.0645, result.get(0).distance(), 1e-3);
        assertEquals("Starbucks Seattle", result.get(1).name());
        assertEquals("Starbucks SF", result.get(2).name());
    }

    @Test
    void findClosest_whenNoValidShops_throwsInvalidDataException() {
        DataFetcher fakeFetcher = source -> "\n  \n";

        CoffeeShopFinder finder = new CoffeeShopFinder(fakeFetcher, new CsvCoffeeShopParser(), new EuclideanDistanceCalculator());

        InvalidDataException ex = assertThrows(InvalidDataException.class,
                () -> finder.findClosest(0, 0, "ignored", 3));

        assertTrue(ex.getMessage().contains("No valid coffee shops found"));
    }

    @Test
    void findClosest_propagatesIOException() {
        DataFetcher failingFetcher = source -> {
            throw new IOException("Network down");
        };

        CoffeeShopFinder finder = new CoffeeShopFinder(failingFetcher, new CsvCoffeeShopParser(), new EuclideanDistanceCalculator());

        assertThrows(IOException.class, () -> finder.findClosest(0, 0, "ignored", 3));
    }
}