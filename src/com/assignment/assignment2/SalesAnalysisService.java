package com.assignment.assignment2;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class performing data analysis using <b>Java Streams</b> and <b>Lambda Expressions</b>.
 * <p>
 * This class fulfills the assignment requirements for:
 * <ul>
 * <li>Functional Programming</li>
 * <li>Data Aggregation</li>
 * <li>Grouping Operations</li>
 * </ul>
 */
public class SalesAnalysisService {

    private final List<SalesRecord> salesData;

    public SalesAnalysisService(List<SalesRecord> salesData) {
        this.salesData = salesData;
    }

    /**
     * REQUIREMENT: Functional Programming (Filtering).
     * Filters the dataset to return only sales from a specific region.
     *
     * @param region The region name to filter by (e.g., "North").
     * @return A list of matching SalesRecords.
     */
    public List<SalesRecord> filterByRegion(String region) {
        return salesData.stream()
                .filter(s -> s.getRegion().equalsIgnoreCase(region)) // Lambda Expression used here
                .collect(Collectors.toList());
    }

    /**
     * REQUIREMENT: Data Aggregation (Sum).
     * Calculates the total revenue across all sales using stream mapping.
     *
     * @return The total sum of amounts.
     */
    public double calculateTotalRevenue() {
        return salesData.stream()
                .mapToDouble(s -> s.getAmount()) // Lambda Expression
                .sum(); // Terminal operation for aggregation
    }

    /**
     * REQUIREMENT: Grouping Operations.
     * Groups sales records by their Category (e.g., "Electronics", "Clothing").
     *
     * @return A Map where Key is the category name and Value is the list of sales.
     */
    public Map<String, List<SalesRecord>> getSalesByCategory() {
        return salesData.stream()
                .collect(Collectors.groupingBy(SalesRecord::getCategory));
    }

    /**
     * REQUIREMENT: Stream Operations (Max).
     * Finds the single sale with the highest monetary amount.
     *
     * @return An Optional containing the highest sale, or empty if no data exists.
     */
    public Optional<SalesRecord> findHighestValueSale() {
        return salesData.stream()
                .max(Comparator.comparingDouble(SalesRecord::getAmount));
    }

    /**
     * REQUIREMENT: Aggregation (Average).
     * Calculates the average transaction value.
     *
     * @return The average amount.
     */
    public double calculateAverageSales() {
        return salesData.stream()
                .mapToDouble(SalesRecord::getAmount)
                .average()
                .orElse(0.0);
    }
}