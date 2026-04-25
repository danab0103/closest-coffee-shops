package com.coffeeshop.presentation;

import com.coffeeshop.application.CoffeeShopDto;

import java.util.List;

public interface Printer {
    void print(List<CoffeeShopDto> results);
}
