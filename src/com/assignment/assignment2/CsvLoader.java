package com.assignment.assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to handle File I/O operations.
 * Responsible for parsing raw CSV data into Java Objects.
 */
public class CsvLoader {

    /**
     * Reads a CSV file from the specified path and converts it into a List of SalesRecord objects.
     *
     * @param filePath The relative or absolute path to the .csv file.
     * @return A list of populated SalesRecord objects.
     */
    public static List<SalesRecord> loadSales(String filePath) {
        List<SalesRecord> sales = new ArrayList<>();

        // Use try-with-resources to ensure the file reader closes automatically (IO Safety)
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                // Skip the first row (Header)
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] v = line.split(",");

                // Parse strings into strongly-typed data (int, double, LocalDate)
                SalesRecord record = new SalesRecord(
                        Integer.parseInt(v[0].trim()),          // ID
                        LocalDate.parse(v[1].trim()),           // Date (YYYY-MM-DD)
                        v[2].trim(),                            // Category
                        v[3].trim(),                            // Product
                        v[4].trim(),                            // Region
                        Double.parseDouble(v[5].trim())         // Amount
                );
                sales.add(record);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return sales;
    }
}