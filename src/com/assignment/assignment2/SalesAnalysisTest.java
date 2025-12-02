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
 * Objectives:
 * 1. Verify "Functional Programming" logic using REAL data.
 * 2. Verify "Stream Operations" correctly process the CSV file.
 * 3. Verify "Data Aggregation" matches the expected values in sales.csv.
 */
class SalesAnalysisTest {

    private SalesAnalysisService service;

    @BeforeEach
    void setUp() {
        // --- INTEGRATION TEST SETUP ---
        // Instead of Mock Data, we load the ACTUAL 'sales.csv' file.
        // This proves that the CsvLoader and the SalesAnalysisService work together.
        List<SalesRecord> realData = CsvLoader.loadSales("sales.csv");

        // Safety assertion to ensure the file was found and read
        assertFalse(realData.isEmpty(), "sales.csv should not be empty! Check file path.");

        service = new SalesAnalysisService(realData);
    }

    /**
     * Requirement: Data Aggregation (Sum)
     * Test: Verifies that the Stream.sum() operation correctly totals the CSV data.
     * Expected Value: 3785.50 (Calculated from the 15 records in sales.csv)
     */
    @Test
    void testCalculateTotalRevenue() {
        assertEquals(3785.50, service.calculateTotalRevenue(), 0.01, "Total revenue incorrect based on CSV data");
    }

    /**
     * Requirement: Data Aggregation (Average)
     * Test: Verifies that Stream.average() works correctly.
     * Expected Value: 3785.50 / 15 = 252.37
     */
    @Test
    void testCalculateAverageSales() {
        assertEquals(252.37, service.calculateAverageSales(), 0.01, "Average calculation incorrect based on CSV data");
    }

    /**
     * Requirement: Functional Programming (Filtering)
     * Test: Verifies that the Lambda filter 's -> s.getRegion().equals("North")' works.
     * Expected: 5 records in the CSV match "North".
     */
    @Test
    void testFilterByRegion() {
        List<SalesRecord> northSales = service.filterByRegion("North");

        assertEquals(5, northSales.size(), "Should find exactly 5 records for North in CSV");
        // Verify every item in the filtered list is actually North
        assertTrue(northSales.stream().allMatch(s -> s.getRegion().equals("North")));
    }

    /**
     * Requirement: Stream Operations (Max)
     * Test: Verifies Comparator logic finds the highest amount.
     * Expected: 1200.00 (Laptop)
     */
    @Test
    void testFindHighestValueSale() {
        Optional<SalesRecord> max = service.findHighestValueSale();

        assertTrue(max.isPresent());
        assertEquals(1200.00, max.get().getAmount(), 0.01, "Highest value should be 1200.00");
    }

    /**
     * Requirement: Grouping and Collections
     * Test: Verifies Collectors.groupingBy logic.
     */
    @Test
    void testGroupingByCategory() {
        Map<String, List<SalesRecord>> grouped = service.getSalesByCategory();

        // Check keys exist (Electronics, Clothing, Home)
        assertTrue(grouped.containsKey("Electronics"));
        assertTrue(grouped.containsKey("Clothing"));
        assertTrue(grouped.containsKey("Home"));

        // Check counts based on CSV
        assertEquals(6, grouped.get("Electronics").size(), "Electronics should have 6 items");
        assertEquals(4, grouped.get("Clothing").size(), "Clothing should have 4 items");
    }
}