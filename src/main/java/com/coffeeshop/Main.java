package com.coffeeshop;

import com.coffeeshop.application.CoffeeShopApp;
import com.coffeeshop.application.CoffeeShopFinder;
import com.coffeeshop.presentation.ConsolePrinter;
import com.coffeeshop.service.implementations.CsvCoffeeShopParser;
import com.coffeeshop.service.implementations.EuclideanDistanceCalculator;
import com.coffeeshop.service.implementations.UrlDataFetcher;
import com.coffeeshop.service.interfaces.CoffeeShopParser;
import com.coffeeshop.service.interfaces.DataFetcher;
import com.coffeeshop.service.interfaces.DistanceCalculator;
import com.coffeeshop.presentation.Printer;

public class Main {
    private static final int MAX_RESULTS = 3;

    public static void main(String[] args) {
        System.exit(buildApp().run(args));
    }

    private static CoffeeShopApp buildApp() {
        DataFetcher dataFetcher = new UrlDataFetcher();
        CoffeeShopParser parser = new CsvCoffeeShopParser();
        DistanceCalculator distanceCalculator = new EuclideanDistanceCalculator();
        CoffeeShopFinder finder = new CoffeeShopFinder(dataFetcher, parser, distanceCalculator);

        Printer printer = new ConsolePrinter();
        return new CoffeeShopApp(finder, printer, MAX_RESULTS);
    }

}
