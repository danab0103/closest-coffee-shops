package com.coffeeshop.service.implementations;

import com.coffeeshop.service.interfaces.DistanceCalculator;

/**
 * Euclidean distance calculator implementation.
 */
public class EuclideanDistanceCalculator implements DistanceCalculator {
    @Override
    public double calculate(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
