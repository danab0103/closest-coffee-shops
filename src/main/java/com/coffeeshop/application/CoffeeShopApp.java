package com.coffeeshop.application;

import com.coffeeshop.exception.InvalidDataException;
import com.coffeeshop.presentation.Printer;
import com.coffeeshop.util.CoordinateParser;

import java.io.IOException;
import java.util.List;

public class CoffeeShopApp {
    private final CoffeeShopFinder finder;
    private final Printer printer;
    private final int maxResults;

    public CoffeeShopApp(CoffeeShopFinder finder, Printer printer, int maxResults) {
        this.finder = finder;
        this.printer = printer;
        this.maxResults = maxResults;
    }

    public int run(String[] args) {
        try {
            validateNumberOfArguments(args);

            double userX = CoordinateParser.parseCoordinate(args[0], "X");
            double userY = CoordinateParser.parseCoordinate(args[1], "Y");
            String dataUrl = args[2];

            List<CoffeeShopDto> results = finder.findClosest(userX, userY, dataUrl, maxResults);
            printer.print(results);

            return 0;

        } catch (IllegalArgumentException | InvalidDataException e) {
            System.err.println("ERROR: " + e.getMessage());
            return 1;

        } catch (IOException e) {
            System.err.println("ERROR: Failed to fetch data - " + e.getMessage());
            return 2;

        } catch (InterruptedException e) {
            System.err.println("ERROR: Request was interrupted - " + e.getMessage());
            Thread.currentThread().interrupt();
            return 3;

        } catch (Exception e) {
            System.err.println("ERROR: Unexpected error - " + e.getMessage());
            e.printStackTrace();
            return 4;
        }
    }

    private static void validateNumberOfArguments(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(
                    String.format("Expected 3 arguments (X, Y, url), got %d", args.length)
            );
        }
    }
}
