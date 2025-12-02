package com.assignment.assignment2;

import java.time.LocalDate;

/**
 * Represents a single sales transaction.
 * <p>
 * This class is designed as an <b>Immutable Data Model</b>, which is ideal for
 * <b>Functional Programming</b> paradigms. Once created, the data cannot change,
 * preventing side effects during Stream operations.
 */
public class SalesRecord {
    private final int id;
    private final LocalDate date;
    private final String category;
    private final String product;
    private final String region;
    private final double amount;

    /**
     * Constructs a new SalesRecord.
     *
     * @param id       Unique identifier for the sale.
     * @param date     Date of the transaction.
     * @param category Product category (e.g., Electronics, Home).
     * @param product  Name of the product.
     * @param region   Geographic region (North, South, etc.).
     * @param amount   Monetary value of the sale.
     */
    public SalesRecord(int id, LocalDate date, String category, String product, String region, double amount) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.product = product;
        this.region = region;
        this.amount = amount;
    }

    // Getters necessary for Stream mapping operations
    public String getCategory() { return category; }
    public String getRegion() { return region; }
    public double getAmount() { return amount; }
    public String getProduct() { return product; }

    @Override
    public String toString() {
        return String.format("Sale{id=%d, product='%s', category='%s', region='%s', amount=%.2f}",
                id, product, category, region, amount);
    }
}