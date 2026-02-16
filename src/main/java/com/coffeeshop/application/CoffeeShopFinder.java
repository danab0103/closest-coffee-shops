package com.coffeeshop.application;

import com.coffeeshop.exception.InvalidDataException;
import com.coffeeshop.model.CoffeeShop;
import com.coffeeshop.service.interfaces.CoffeeShopParser;
import com.coffeeshop.service.interfaces.DataFetcher;
import com.coffeeshop.service.interfaces.DistanceCalculator;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class CoffeeShopFinder {

    private final DataFetcher dataFetcher;
    private final CoffeeShopParser parser;
    private final DistanceCalculator distanceCalculator;

    /**
     * Constructor with dependency injection for flexibility.
     * All dependencies are interfaces, following Dependency Inversion Principle.
     */
    public CoffeeShopFinder(DataFetcher dataFetcher, CoffeeShopParser parser, DistanceCalculator distanceCalculator) {
        this.dataFetcher = dataFetcher;
        this.parser = parser;
        this.distanceCalculator = distanceCalculator;
    }

    public List<CoffeeShopDto> findClosest(double userX, double userY, String dataUrl, int limit)
            throws InvalidDataException, IOException, InterruptedException {

        String rawData = dataFetcher.fetchData(dataUrl);
        List<CoffeeShop> coffeeShops = parser.parse(rawData);

        if (coffeeShops.isEmpty()) {
            throw new InvalidDataException("No valid coffee shops found in the data");
        }

        return coffeeShops.stream()
                .map(shop -> {
                    double distance = distanceCalculator.calculate(userX, userY, shop.x(), shop.y());
                    return new CoffeeShopDto(shop.name(), distance);
                })
                .sorted(Comparator.comparingDouble(CoffeeShopDto::distance))
                .limit(limit)
                .toList();
    }
}