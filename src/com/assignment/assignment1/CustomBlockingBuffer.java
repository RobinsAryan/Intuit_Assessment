package com.assignment.assignment1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A concrete implementation of a thread-safe shared buffer (Monitor Object).
 * <p>
 * This class handles the synchronization logic using Java's built-in
 * monitor mechanism (synchronized, wait, notifyAll).
 *
 * @param <T> The type of data stored in the buffer.
 */
public class CustomBlockingBuffer<T> implements SharedBuffer<T> {

    // The internal storage for the buffer
    private final Queue<T> queue;

    // The maximum number of items the buffer can hold before blocking producers
    private final int capacity;

    // Flag to signal consumers to stop waiting when production is complete
    private boolean productionFinished = false;

    /**
     * Constructor to initialize the buffer.
     * * @param capacity The fixed size of the buffer.
     */
    public CustomBlockingBuffer(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    /**
     * Adds an item to the buffer in a thread-safe manner.
     * Blocks the calling thread if the buffer is full.
     */
    @Override
    public synchronized void produce(T item) throws InterruptedException {
        // Critical Section: Only one thread can execute this block at a time.

        // "While" loop is used instead of "If" to handle spurious wakeups.
        // If the buffer is full, the producer must wait.
        while (queue.size() == capacity) {
            System.out.println(Thread.currentThread().getName() + ": Buffer FULL. Waiting...");
            wait(); // Releases the lock and waits until notified
        }

        // Add the item to the queue
        queue.add(item);
        System.out.println(Thread.currentThread().getName() + ": Added " + item + " | Size: " + queue.size());

        // Notify all waiting threads (specifically Consumers) that data is available
        notifyAll();
    }

    /**
     * Removes an item from the buffer in a thread-safe manner.
     * Blocks the calling thread if the buffer is empty.
     */
    @Override
    public synchronized T consume() throws InterruptedException {
        // Critical Section

        // Wait while the queue is empty
        while (queue.isEmpty()) {
            // Check if production is finished. If so, return null to signal exit.
            if (productionFinished) {
                return null;
            }
            System.out.println(Thread.currentThread().getName() + ": Buffer EMPTY. Waiting...");
            wait(); // Releases the lock and waits until notified
        }

        // Retrieve and remove the head of the queue
        T item = queue.poll();
        System.out.println(Thread.currentThread().getName() + ": Removed " + item + " | Size: " + queue.size());

        // Notify all waiting threads (specifically Producers) that space is available
        notifyAll();

        return item;
    }

    /**
     * Signals that the producer has finished its job.
     * We must notifyAll() here to wake up any consumers currently stuck in wait(),
     * allowing them to check the flag and exit gracefully.
     */
    @Override
    public synchronized void setDone(boolean done) {
        this.productionFinished = done;
        notifyAll(); // Wake up waiting consumers so they can terminate
    }
}