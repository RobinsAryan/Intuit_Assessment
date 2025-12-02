package com.assignment.assignment2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration Test Suite for Assignment 2.
 * <p>
 * This class maps 1-to-1 with the Assignment Testing Objectives:
 * 1. Functional Programming
 * 2. Stream Operations
 * 3. Data Aggregation
 * 4. Lambda Expressions
 */
class SalesAnalysisTest {

    private SalesAnalysisService service;

    @BeforeEach
    void setUp() {
        // --- INTEGRATION TEST SETUP ---
        // We load the ACTUAL 'sales.csv' file to prove the system works end-to-end.
        List<SalesRecord> realData = CsvLoader.loadSales("sales.csv");

        // Safety assertion to ensure file is read correctly
        assertFalse(realData.isEmpty(), "sales.csv should not be empty! Check file path.");

        service = new SalesAnalysisService(realData);
    }

    // ---------------------------------------------------------------
    // OBJECTIVE 1: Functional Programming
    // Proof: We transform the data structure (List -> Map) using functional collectors
    // without manually iterating or managing state.
    // ---------------------------------------------------------------
    @Test
    void testFunctionalProgramming() {
        Map<String, List<SalesRecord>> grouped = service.getSalesByCategory();

        // Check keys exist (Electronics, Clothing, Home)
        assertTrue(grouped.containsKey("Electronics"));
        assertTrue(grouped.containsKey("Clothing"));
        assertTrue(grouped.containsKey("Home"));

        // Check functional transformation correctness
        assertEquals(6, grouped.get("Electronics").size(), "Electronics should have 6 items");
        assertEquals(4, grouped.get("Clothing").size(), "Clothing should have 4 items");
    }

    // ---------------------------------------------------------------
    // OBJECTIVE 2: Stream Operations
    // Proof: We uses complex stream operations (.max(), Comparator) to find a specific element.
    // ---------------------------------------------------------------
    @Test
    void testStreamOperations() {
        Optional<SalesRecord> max = service.findHighestValueSale();

        // Verify the Optional result from the stream
        assertTrue(max.isPresent());
        // 1200.00 is the highest price in the provided CSV (Laptop)
        assertEquals(1200.00, max.get().getAmount(), 0.01, "Highest value incorrect");
        assertEquals("Laptop", max.get().getProduct());
    }

    // ---------------------------------------------------------------
    // OBJECTIVE 3: Data Aggregation
    // Proof: We use .mapToDouble(), .sum(), and .average() to reduce data to single values.
    // ---------------------------------------------------------------
    @Test
    void testDataAggregation() {
        // Test SUM
        // Total of all 15 records in sales.csv is 3785.50
        assertEquals(3785.50, service.calculateTotalRevenue(), 0.01, "Total Revenue (Sum) Failed");

        // Test AVERAGE
        // 3785.50 / 15 = 252.366...
        assertEquals(252.37, service.calculateAverageSales(), 0.01, "Average Sales Failed");
    }

    // ---------------------------------------------------------------
    // OBJECTIVE 4: Lambda Expressions
    // Proof: We use explicit Lambda expressions (s -> s.getRegion()...) to filter data.
    // ---------------------------------------------------------------
    @Test
    void testLambdaExpressions() {
        // The filter logic uses a Lambda: s -> s.getRegion().equalsIgnoreCase(region)
        List<SalesRecord> northSales = service.filterByRegion("North");

        // There are exactly 5 records in the 'North' region in the CSV
        assertEquals(5, northSales.size(), "Lambda filter failed to find correct count");

        // Use another Lambda here to verify the result list
        assertTrue(northSales.stream().allMatch(s -> s.getRegion().equals("North")));
    }
}