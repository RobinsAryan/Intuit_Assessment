package com.assignment.assignment2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for SalesAnalysisService.
 * <p>
 * Objectives:
 * 1. Verify Functional Programming logic (Streams/Lambdas).
 * 2. Verify Data Aggregation accuracy (Sum, Average).
 * 3. Verify Grouping operations.
 * <p>
 * Note: These tests use Mock Data (in-memory list) rather than reading from the CSV.
 * This ensures we are testing the Logic, not the File System.
 */
class SalesAnalysisTest {

    private SalesAnalysisService service;

    @BeforeEach
    void setUp() {
        // Prepare Mock Data for functional testing
        // Total Sum = 1000 + 50 + 150 + 200 = 1400.0
        // Count = 4
        // Average = 350.0
        List<SalesRecord> mockData = Arrays.asList(
                new SalesRecord(1, LocalDate.now(), "Tech", "Laptop", "North", 1000.0),
                new SalesRecord(2, LocalDate.now(), "Tech", "Mouse", "South", 50.0),
                new SalesRecord(3, LocalDate.now(), "Home", "Table", "North", 150.0),
                new SalesRecord(4, LocalDate.now(), "Home", "Chair", "West", 200.0)
        );
        service = new SalesAnalysisService(mockData);
    }

    /**
     * Tests Data Aggregation (Summing).
     * Expected: 1000 + 50 + 150 + 200 = 1400.0
     */
    @Test
    void testCalculateTotalRevenue() {
        assertEquals(1400.0, service.calculateTotalRevenue(), "Total revenue calculation failed");
    }

    /**
     * Tests Data Aggregation (Average).
     * Expected: 1400.0 / 4 items = 350.0
     */
    @Test
    void testCalculateAverageSales() {
        assertEquals(350.0, service.calculateAverageSales(), "Average calculation failed");
    }

    /**
     * Tests Stream Filtering with Lambda expressions.
     * Expects 2 records for 'North' region (Laptop and Table).
     */
    @Test
    void testFilterByRegion() {
        List<SalesRecord> northSales = service.filterByRegion("North");

        assertEquals(2, northSales.size(), "Should find exactly 2 records for North");
        assertTrue(northSales.stream().allMatch(s -> s.getRegion().equals("North")), "Filter leaked non-North regions");
    }

    /**
     * Tests Finding Max value using Comparators.
     * Expected: Laptop (1000.0)
     */
    @Test
    void testFindHighestValueSale() {
        Optional<SalesRecord> max = service.findHighestValueSale();

        assertTrue(max.isPresent(), "Max value should be present");
        assertEquals("Laptop", max.get().getProduct());
        assertEquals(1000.0, max.get().getAmount());
    }

    /**
     * Tests Grouping by Category using Collectors.
     * Expects keys "Tech" and "Home".
     */
    @Test
    void testGroupingByCategory() {
        Map<String, List<SalesRecord>> grouped = service.getSalesByCategory();

        assertTrue(grouped.containsKey("Tech"));
        assertTrue(grouped.containsKey("Home"));
        assertEquals(2, grouped.get("Tech").size(), "Tech category should have 2 items");
    }
}