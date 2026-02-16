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
    private static final int EXPECTED_FIELD_COUNT = 3;

    @Override
    public List<CoffeeShop> parse(String csvContent) throws InvalidDataException {
        List<CoffeeShop> coffeeShops = new ArrayList<>();

        String[] lines = csvContent.split("\\r?\\n");
        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;
            if (isEmptyLine(line)) {
                continue;
            }

            CoffeeShop shop = parseLine(line, lineNumber);
            coffeeShops.add(shop);
        }

        return coffeeShops;
    }

    private CoffeeShop parseLine(String line, int lineNumber) throws InvalidDataException {
        String[] parts = line.split(",");
        validateFieldCount(parts, lineNumber);

        String name = parts[0].trim();
        String xCoordinateStr = parts[1].trim();
        String yCoordinateStr = parts[2].trim();

        validateName(name, lineNumber);
        double x = CoordinateParser.parseCoordinateFromCsv(xCoordinateStr, lineNumber, "X");
        double y = CoordinateParser.parseCoordinateFromCsv(yCoordinateStr, lineNumber, "Y");

        return new CoffeeShop(name, x, y);
    }

    private boolean isEmptyLine(String line) {
        return line.trim().isEmpty();
    }

    private void validateFieldCount(String[] parts, int lineNumber) throws InvalidDataException {
        if (parts.length != EXPECTED_FIELD_COUNT) {
            throw new InvalidDataException(String.format("Line %d: Invalid CSV format. Expected %d fields (Name,Y,X), got %d",
                            lineNumber, EXPECTED_FIELD_COUNT, parts.length));
        }
    }

    private void validateName(String name, int lineNumber) throws InvalidDataException {
        if (name.isEmpty()) {
            throw new InvalidDataException(String.format("Line %d: Coffee shop name cannot be empty", lineNumber));
        }
    }
}