package com.coffeeshop;

import com.coffeeshop.application.CoffeeShopDto;
import com.coffeeshop.application.CoffeeShopFinder;
import com.coffeeshop.exception.InvalidDataException;
import com.coffeeshop.service.interfaces.*;
import com.coffeeshop.service.implementations.*;
import com.coffeeshop.util.CoordinateParser;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final int MAX_RESULTS = 3;

    public static void main(String[] args) {
        try {
            validateNumberOfArguments(args);
            double userX = CoordinateParser.parseCoordinate(args[0], "X");
            double userY = CoordinateParser.parseCoordinate(args[1], "Y");
            String dataUrl = args[2];

            DataFetcher dataFetcher = new UrlDataFetcher();
            CoffeeShopParser parser = new CsvCoffeeShopParser();
            DistanceCalculator distanceCalculator = new EuclideanDistanceCalculator();
            CoffeeShopFinder finder = new CoffeeShopFinder(dataFetcher, parser, distanceCalculator);

            List<CoffeeShopDto> results = finder.findClosest(userX, userY, dataUrl, MAX_RESULTS);
            displayResults(results);
        } catch (InvalidDataException e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(1);

        } catch (IOException e) {
            System.err.println("ERROR: Failed to fetch data - " + e);
            System.exit(2);

        } catch (InterruptedException e) {
            System.err.println("ERROR: Request was interrupted - " + e.getMessage());
            Thread.currentThread().interrupt();
            System.exit(3);

        } catch (IllegalArgumentException e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(4);

        } catch (Exception e) {
            System.err.println("ERROR: Unexpected error - " + e.getMessage());
            e.printStackTrace();
            System.exit(5);
        }
    }

    private static void validateNumberOfArguments(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(String.format("Expected 3 arguments, got %d", args.length));
        }
    }

    private static void displayResults(List<CoffeeShopDto> results) {
        for (CoffeeShopDto coffeeShopDto : results) {
            System.out.printf("%s,%.4f%n", coffeeShopDto.name(), coffeeShopDto.distance());
        }
    }
}