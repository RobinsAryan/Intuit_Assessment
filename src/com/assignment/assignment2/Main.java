package com.assignment.assignment2;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Assignment 2: Sales Data Analysis ---");

        // 1. Load Data
        List<SalesRecord> data = CsvLoader.loadSales("sales.csv");
        if (data.isEmpty()) {
            System.out.println("No data found. Ensure 'sales.csv' is in the project root.");
            return;
        }
        System.out.println("Loaded " + data.size() + " records.");

        // 2. Process Data
        SalesAnalysisService service = new SalesAnalysisService(data);

        // 3. Demonstrate Requirements

        // A. Aggregation
        System.out.println("\n--- [Aggregation] Revenue & Average ---");
        System.out.printf("Total Revenue: $%.2f%n", service.calculateTotalRevenue());
        System.out.printf("Average Sale:  $%.2f%n", service.calculateAverageSales());

        // B. Finding Max
        System.out.println("\n--- [Stream Max] Highest Value Sale ---");
        service.findHighestValueSale().ifPresent(System.out::println);

        // C. Filtering
        System.out.println("\n--- [Filtering] Sales in North Region ---");
        service.filterByRegion("North").forEach(System.out::println);

        // D. Grouping
        System.out.println("\n--- [Grouping] Sales by Category ---");
        Map<String, List<SalesRecord>> groupedSales = service.getSalesByCategory();

        groupedSales.forEach((category, salesList) -> {
            System.out.println("Category: " + category + " (" + salesList.size() + " items)");
            // Optional: print the first item of each group to verify
            System.out.println("  -> Example: " + salesList.get(0).getProduct());
        });
    }
}