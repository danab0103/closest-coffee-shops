package com.coffeeshop.service.implementations;

import com.coffeeshop.exception.InvalidDataException;
import com.coffeeshop.model.CoffeeShop;
import com.coffeeshop.service.interfaces.CoffeeShopParser;
import com.coffeeshop.util.CoordinateParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses coffee shop data from CSV format.
 */
public class CsvCoffeeShopParser implements CoffeeShopParser {
    @Override
    public List<CoffeeShop> parse(String csvContent) throws InvalidDataException {
        List<CoffeeShop> coffeeShops = new ArrayList<>();

        String[] lines = csvContent.split("\\r?\\n");

        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;

            if (line.trim().isEmpty()) {
                continue;
            }

            CoffeeShop shop = parseLine(line, lineNumber);
            coffeeShops.add(shop);
        }

        return coffeeShops;
    }

    private CoffeeShop parseLine(String line, int lineNumber) throws InvalidDataException {
        String[] parts = line.split(",");

        if (parts.length != 3) {
            throw new InvalidDataException(String.format("Line %d: Invalid CSV format. Expected 3 fields (Name,Y,X), got %d",
                            lineNumber, parts.length));
        }

        String name = parts[0].trim();
        String xCoordinateStr = parts[1].trim();
        String yCoordinateStr = parts[2].trim();

        if (name.isEmpty()) {
            throw new InvalidDataException(String.format("Line %d: Coffee shop name cannot be empty", lineNumber));
        }

        double x = CoordinateParser.parseCoordinateFromCsv(xCoordinateStr, lineNumber, "X");
        double y = CoordinateParser.parseCoordinateFromCsv(yCoordinateStr, lineNumber, "Y");

        return new CoffeeShop(name, x, y);
    }
}