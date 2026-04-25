package com.coffeeshop.service.implementations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EuclideanDistanceCalculatorTest {
    @Test
    void calculate_returnsCorrectDistance() {
        EuclideanDistanceCalculator calculator = new EuclideanDistanceCalculator();

        double result = calculator.calculate(0, 0, 3, 4);

        assertEquals(5.0, result, 1e-12);
    }
}