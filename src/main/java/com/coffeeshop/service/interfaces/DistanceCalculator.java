package com.coffeeshop.service.interfaces;

/**
 * Interface for calculating distances between two points.
 * Allows for different distance calculation algorithms (Euclidean, Manhattan, etc.)
 */
public interface DistanceCalculator {
    double calculate(double x1, double y1, double x2, double y2);
}
