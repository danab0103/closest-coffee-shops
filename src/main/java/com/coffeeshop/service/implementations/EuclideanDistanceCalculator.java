package com.coffeeshop.service.implementations;

import com.coffeeshop.service.interfaces.DistanceCalculator;

public class EuclideanDistanceCalculator implements DistanceCalculator {
    @Override
    public double calculate(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        return Math.round(distance * 10000.0) / 10000.0;
    }
}
