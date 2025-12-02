package com.assignment.assignment1;

import java.util.List;

/**
 * Represents the Consumer thread in the Producer-Consumer pattern.
 * <p>
 * Responsibilities:
 * 1. Read items from the SharedBuffer.
 * 2. Simulate processing time (slower than producer).
 * 3. Store results in a destination container.
 * 4. Stop gracefully when the buffer signals completion.
 */
public class Consumer extends AbstractWorker {

    // The container where processed items are stored (e.g., a results list)
    private final List<Integer> destination;

    /**
     * Constructor for the Consumer.
     *
     * @param buffer The shared buffer to read data from.
     * @param destination The list where consumed items will be saved.
     */
    public Consumer(SharedBuffer<Integer> buffer, List<Integer> destination) {
        super(buffer); // Pass buffer to parent AbstractWorker
        this.destination = destination;
    }

    /**
     * The main execution logic of the Consumer thread.
     */
    @Override
    public void run() {
        try {
            // Continuously consume items until the termination signal is received
            while (true) {
                // Simulate processing time.
                // Note: 250ms is slower than the Producer's 100ms.
                // This intentional delay causes the buffer to fill up, forcing the
                // Producer to wait (demonstrating the Blocking Queue behavior).
                Thread.sleep(250);

                // Attempt to take an item from the buffer.
                // If the buffer is empty, this call will BLOCK (wait) until data is available.
                Integer item = buffer.consume();

                // Check for termination signal.
                // The buffer returns null when it is empty AND production is finished.
                if (item == null) {
                    break; // Exit the loop to finish the thread
                }

                // Store the consumed item
                destination.add(item);
            }
            System.out.println("Consumer: Job Finished.");

        } catch (InterruptedException e) {
            // Handle thread interruption
            Thread.currentThread().interrupt();
        }
    }
}