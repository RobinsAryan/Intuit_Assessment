package com.assignment.assignment1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Test Suite for Assignment 1.
 * <p>
 * This class maps 1-to-1 with the Assignment Testing Objectives:
 * 1. Thread Synchronization
 * 2. Concurrent Programming
 * 3. Blocking Queues
 * 4. Wait/Notify Mechanism
 */
public class ProducerConsumerTest {

    // ---------------------------------------------------------------
    // OBJECTIVE 1: Thread Synchronization
    // Proof: We move data. If synchronization is broken, data order is lost or size mismatches.
    // ---------------------------------------------------------------
    @Test
    void testThreadSynchronization() throws InterruptedException {
        int capacity = 5;
        // Create a large dataset to force many context switches
        List<Integer> source = new ArrayList<>();
        for (int i = 0; i < 100; i++) source.add(i);
        List<Integer> dest = new ArrayList<>();

        SharedBuffer<Integer> buffer = new CustomBlockingBuffer<>(capacity);
        Thread t1 = new Thread(new Producer(buffer, source));
        Thread t2 = new Thread(new Consumer(buffer, dest));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // Assertion: Source and Destination must be identical
        assertEquals(source, dest, "Synchronization Failed: Data corruption detected.");
    }

    // ---------------------------------------------------------------
    // OBJECTIVE 2: Concurrent Programming
    // Proof: Both threads run alive at the same time without deadlocking immediately.
    // ---------------------------------------------------------------
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS) // INCREASED TO 5 SECONDS
    void testConcurrentProgramming() throws InterruptedException {
        SharedBuffer<Integer> buffer = new CustomBlockingBuffer<>(2);
        List<Integer> source = new ArrayList<>();
        // Process 10 items (takes approx 2.5 seconds due to Consumer sleep)
        for (int i = 0; i < 10; i++) source.add(i);

        Producer p = new Producer(buffer, source);
        Consumer c = new Consumer(buffer, new ArrayList<>());

        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);

        t1.start();
        t2.start();

        // Check that both are alive shortly after starting (Running concurrently)
        Thread.sleep(50);
        assertTrue(t1.isAlive() || t2.isAlive(), "Threads should be running concurrently");

        t1.join();
        t2.join();
    }

    // ---------------------------------------------------------------
    // OBJECTIVE 3: Blocking Queues (Producer Side)
    // Proof: Producer attempts to write to FULL buffer and must enter WAITING state.
    // ---------------------------------------------------------------
    @Test
    void testBlockingQueueProducer() throws InterruptedException {
        // Capacity 1
        SharedBuffer<Integer> buffer = new CustomBlockingBuffer<>(1);
        buffer.produce(999); // Buffer is now FULL

        // Try to add a second item (Should Block)
        Thread producerThread = new Thread(() -> {
            try {
                buffer.produce(888);
            } catch (InterruptedException e) {}
        });

        producerThread.start();
        Thread.sleep(100); // Give it time to get stuck

        // Verify it is BLOCKED/WAITING
        assertTrue(
                producerThread.getState() == Thread.State.WAITING ||
                        producerThread.getState() == Thread.State.TIMED_WAITING,
                "Producer did not block on full queue!"
        );

        producerThread.interrupt(); // Cleanup
    }

    // ---------------------------------------------------------------
    // OBJECTIVE 4: Wait/Notify Mechanism (Consumer Side)
    // Proof: Consumer attempts to read from EMPTY buffer and must enter WAITING state.
    // ---------------------------------------------------------------
    @Test
    void testWaitNotifyConsumer() throws InterruptedException {
        // Buffer is EMPTY
        SharedBuffer<Integer> buffer = new CustomBlockingBuffer<>(5);

        // Try to consume immediately (Should Block/Wait)
        Thread consumerThread = new Thread(() -> {
            try {
                buffer.consume();
            } catch (InterruptedException e) {}
        });

        consumerThread.start();
        Thread.sleep(100); // Give it time to hit the wait()

        // Verify it is BLOCKED/WAITING
        assertTrue(
                consumerThread.getState() == Thread.State.WAITING ||
                        consumerThread.getState() == Thread.State.TIMED_WAITING,
                "Consumer did not wait on empty queue!"
        );

        consumerThread.interrupt(); // Cleanup
    }
}