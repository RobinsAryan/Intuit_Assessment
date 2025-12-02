package com.assignment.assignment1;

/**
 * Abstract base class for all worker threads (Producers and Consumers).
 * <p>
 * This class implements the Runnable interface and holds the common
 * reference to the shared buffer. This demonstrates the OOP principle
 * of Inheritance to avoid code duplication.
 */
public abstract class AbstractWorker implements Runnable {

    // Protected visibility allows subclasses (Producer/Consumer) to access the buffer directly
    protected final SharedBuffer<Integer> buffer;

    /**
     * Constructor for the abstract worker.
     * * @param buffer The shared buffer instance that this worker will interact with.
     */
    public AbstractWorker(SharedBuffer<Integer> buffer) {
        this.buffer = buffer;
    }
}