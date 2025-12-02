package com.assignment.assignment1;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main Entry Point (Driver) for the Producer-Consumer application.
 * <p>
 * This class demonstrates the "Orchestrator" role:
 * 1. Configures the environment (Data and Buffer size).
 * 2. Initializes the worker threads (Producer and Consumer).
 * 3. Manages the Thread Lifecycle (Start and Join).
 * 4. Validates the final result to prove Thread Safety.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("--- Assignment 1: Producer-Consumer Pattern ---");

        // ---------------------------------------------------------------
        // 1. SETUP & CONFIGURATION
        // ---------------------------------------------------------------

        // We set a small capacity (3) intentionally.
        // Since we are moving 10 items, the buffer WILL fill up.
        // This forces the Producer to block (wait), satisfying the assignment requirement.
        int bufferCapacity = 3;

        // Source: The "Database" or input stream we want to process.
        List<Integer> sourceData = new ArrayList<>();

        // Destination: Where the processed data ends up.
        List<Integer> finalData = new ArrayList<>();

        // Populate dummy data (numbers 1 to 10)
        for (int i = 1; i <= 10; i++) sourceData.add(i);

        // ---------------------------------------------------------------
        // 2. INITIALIZATION (Dependency Injection)
        // ---------------------------------------------------------------

        // We use the Interface type (SharedBuffer) for the variable, but the
        // Concrete type (CustomBlockingBuffer) for the instance.
        // This is known as "Programming to an Interface" (Polymorphism).
        SharedBuffer<Integer> buffer = new CustomBlockingBuffer<>(bufferCapacity);

        // Inject the shared buffer and data containers into the workers
        Producer producer = new Producer(buffer, sourceData);
        Consumer consumer = new Consumer(buffer, finalData);

        // ---------------------------------------------------------------
        // 3. EXECUTION (Thread Management)
        // ---------------------------------------------------------------

        // Wrap the Runnable workers in actual Java Thread objects
        Thread t1 = new Thread(producer, "PRODUCER");
        Thread t2 = new Thread(consumer, "CONSUMER");

        // Start concurrent execution. The JVM scheduler will now toggle between them.
        t1.start();
        t2.start();

        // The Main thread must wait for workers to finish before verifying results.
        // .join() puts the Main thread in a waiting state until t1 and t2 die.
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ---------------------------------------------------------------
        // 4. VALIDATION
        // ---------------------------------------------------------------
        System.out.println("--- Complete ---");

        // Verify Data Integrity:
        // If the synchronization logic is correct, the source and destination
        // lists must be identical in size, values, and order.
        if (sourceData.equals(finalData)) {
            System.out.println("SUCCESS: Integrity Verified. Thread synchronization worked.");
        } else {
            System.out.println("FAILURE: Data Mismatch. Race condition occurred.");
        }
    }
}