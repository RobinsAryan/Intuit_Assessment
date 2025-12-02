package com.assignment.assignment1;

import java.util.List;

/**
 * Represents the Producer thread in the Producer-Consumer pattern.
 * <p>
 * Responsibilities:
 * 1. Read data from a source container (List).
 * 2. Simulate work (processing time).
 * 3. Place items into the SharedBuffer.
 * 4. Signal when production is complete.
 */
public class Producer extends AbstractWorker {

    // The source of data to be produced (e.g., a list of numbers)
    private final List<Integer> source;

    /**
     * Constructor for the Producer.
     *
     * @param buffer The shared buffer to write data into.
     * @param source The list of items to be processed and sent to the buffer.
     */
    public Producer(SharedBuffer<Integer> buffer, List<Integer> source) {
        super(buffer); // Pass buffer to parent AbstractWorker
        this.source = source;
    }

    /**
     * The main execution logic of the Producer thread.
     */
    @Override
    public void run() {
        try {
            // Iterate through every item in the source list
            for (Integer item : source) {
                // Simulate some time-consuming task (e.g., reading from DB or network)
                Thread.sleep(100);

                // Attempt to put the item into the shared buffer.
                // If the buffer is full, this call will BLOCK (wait) until space is available.
                buffer.produce(item);
            }

            // Critical Step: Signal the buffer that no more data is coming.
            // This prevents the Consumer from waiting forever (deadlock).
            buffer.setDone(true);

            System.out.println("Producer: Job Finished.");

        } catch (InterruptedException e) {
            // Handle thread interruption (e.g., if the application is stopping)
            Thread.currentThread().interrupt();
        }
    }
}