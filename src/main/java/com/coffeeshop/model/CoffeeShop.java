package com.coffeeshop.model;

/**
 * Represents a coffee shop with its name and location coordinates.
 * Immutable record for data integrity.
 */
public record CoffeeShop(String name, double x, double y){
}
