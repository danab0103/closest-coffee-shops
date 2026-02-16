package com.coffeeshop.application;

import com.coffeeshop.exception.InvalidDataException;
import com.coffeeshop.presentation.Printer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class CoffeeShopAppTest {

    @Test
    void run_success_returns0() throws Exception {
        CoffeeShopFinder finder = Mockito.mock(CoffeeShopFinder.class);
        Printer printer = Mockito.mock(Printer.class);

        List<CoffeeShopDto> fakeResults = List.of(
                new CoffeeShopDto("A", 1.2345),
                new CoffeeShopDto("B", 2.3456)
        );

        Mockito.when(finder.findClosest(eq(1.0), eq(2.0), eq("https://test.com"), eq(3)))
                .thenReturn(fakeResults);

        CoffeeShopApp app = new CoffeeShopApp(finder, printer, 3);

        int code = app.run(new String[]{"1.0", "2.0", "https://test.com"});

        assertEquals(0, code);
        Mockito.verify(finder).findClosest(1.0, 2.0, "https://test.com", 3);
        Mockito.verify(printer).print(fakeResults);
    }

    @Test
    void run_wrongNumberOfArgs_returns1() {
        CoffeeShopFinder finder = Mockito.mock(CoffeeShopFinder.class);
        Printer printer = Mockito.mock(Printer.class);

        CoffeeShopApp app = new CoffeeShopApp(finder, printer, 3);

        int code = app.run(new String[]{"1.0", "2.0"});

        assertEquals(1, code);
    }

    @Test
    void run_invalidCoordinate_returns1() {
        CoffeeShopFinder finder = Mockito.mock(CoffeeShopFinder.class);
        Printer printer = Mockito.mock(Printer.class);

        CoffeeShopApp app = new CoffeeShopApp(finder, printer, 3);

        int code = app.run(new String[]{"abc", "2.0", "https://test.com"});

        assertEquals(1, code);
    }

    @Test
    void run_whenFinderThrowsInvalidDataException_returns1() throws Exception {
        CoffeeShopFinder finder = Mockito.mock(CoffeeShopFinder.class);
        Printer printer = Mockito.mock(Printer.class);

        Mockito.when(finder.findClosest(anyDouble(), anyDouble(), anyString(), anyInt()))
                .thenThrow(new InvalidDataException("Bad data"));

        CoffeeShopApp app = new CoffeeShopApp(finder, printer, 3);

        int code = app.run(new String[]{"1.0", "2.0", "https://test.com"});

        assertEquals(1, code);
    }

    @Test
    void run_whenFinderThrowsIOException_returns2() throws Exception {
        CoffeeShopFinder finder = Mockito.mock(CoffeeShopFinder.class);
        Printer printer = Mockito.mock(Printer.class);

        Mockito.when(finder.findClosest(anyDouble(), anyDouble(), anyString(), anyInt()))
                .thenThrow(new IOException("Network error"));

        CoffeeShopApp app = new CoffeeShopApp(finder, printer, 3);

        int code = app.run(new String[]{"1.0", "2.0", "https://test.com"});

        assertEquals(2, code);
        Mockito.verify(finder).findClosest(1.0, 2.0, "https://test.com", 3);
    }

    @Test
    void run_whenFinderThrowsInterruptedException_returns3_andSetsInterruptFlag() throws Exception {
        CoffeeShopFinder finder = Mockito.mock(CoffeeShopFinder.class);
        Printer printer = Mockito.mock(Printer.class);

        Mockito.when(finder.findClosest(anyDouble(), anyDouble(), anyString(), anyInt()))
                .thenThrow(new InterruptedException("Interrupted"));

        CoffeeShopApp app = new CoffeeShopApp(finder, printer, 3);

        assertFalse(Thread.currentThread().isInterrupted());

        int code = app.run(new String[]{"1.0", "2.0", "https://test.com"});

        assertEquals(3, code);
        assertTrue(Thread.currentThread().isInterrupted());

        Thread.interrupted();
    }

    @Test
    void run_whenUnexpectedException_returns4() throws Exception {
        CoffeeShopFinder finder = Mockito.mock(CoffeeShopFinder.class);
        Printer printer = Mockito.mock(Printer.class);

        Mockito.when(finder.findClosest(anyDouble(), anyDouble(), anyString(), anyInt()))
                .thenThrow(new RuntimeException("Unexpected error"));

        CoffeeShopApp app = new CoffeeShopApp(finder, printer, 3);

        int code = app.run(new String[]{"1.0", "2.0", "https://test.com"});

        assertEquals(4, code);
    }
}
