package com.assignment.assignment1;

/**
 * Interface defining the contract for a shared thread-safe buffer.
 * This acts as the bridge between Producer and Consumer threads.
 *
 * @param <T> The type of data elements stored in the buffer.
 */
public interface SharedBuffer<T> {

    /**
     * Adds an item to the buffer.
     * <p>
     * If the buffer is full, the calling thread must wait (block)
     * until space becomes available.
     *
     * @param item The item to produce/add.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    void produce(T item) throws InterruptedException;

    /**
     * Removes and returns an item from the buffer.
     * <p>
     * If the buffer is empty, the calling thread must wait (block)
     * until an item becomes available.
     *
     * @return The next item from the buffer, or null if production is done.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    T consume() throws InterruptedException;

    /**
     * Sets a flag indicating that the Producer has finished its job.
     * This is used to signal Consumers to stop waiting and exit safely.
     *
     * @param done True if production is complete.
     */
    void setDone(boolean done);
}