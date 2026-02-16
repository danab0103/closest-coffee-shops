package com.coffeeshop.presentation;

import com.coffeeshop.application.CoffeeShopDto;

import java.util.List;

public class ConsolePrinter implements Printer {
    @Override
    public void print(List<CoffeeShopDto> results) {
        for (CoffeeShopDto dto : results) {
            System.out.printf("%s,%.4f%n", dto.name(), dto.distance());
        }
    }
}
