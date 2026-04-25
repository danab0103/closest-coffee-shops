package com.coffeeshop.service.interfaces;

import com.coffeeshop.exception.InvalidDataException;
import com.coffeeshop.model.CoffeeShop;

import java.util.List;

/**
 * Interface for parsing coffee shop data from various formats (CSV, JSON, XML, etc.).
 * Allows different parser implementations.
 */
public interface CoffeeShopParser {
    List<CoffeeShop> parse(String content) throws InvalidDataException;
}