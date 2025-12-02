package com.assignment.assignment1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Unit Test Suite for the Producer-Consumer Application.
 * <p>
 * This class verifies the core technical requirements of the assignment:
 * 1. Thread Synchronization (Data integrity under concurrent execution).
 * 2. Flexibility (Handling different buffer configurations).
 * 3. Blocking Behavior (Verifying threads wait when necessary).
 */
public class ProducerConsumerTest {

    /**
     * REQUIREMENT: Flexibility & Concurrent Programming.
     * <p>
     * This Parameterized Test verifies that the system functions correctly
     * under different buffer capacities. It runs automatically three times:
     * <ul>
     * <li>Capacity 1: Forces heavy blocking (Ping-Pong behavior).</li>
     * <li>Capacity 3: Balanced blocking (Normal behavior).</li>
     * <li>Capacity 50: No blocking (High throughput).</li>
     * </ul>
     *
     * @param capacity The buffer size to be tested in this run.
     * @throws InterruptedException If threads are interrupted during execution.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 50})
    void testDifferentCapacities(int capacity) throws InterruptedException {
        System.out.println("Running Test with Capacity: " + capacity);

        // 1. Setup Data
        List<Integer> source = new ArrayList<>();
        // Create 20 items to move
        for (int i = 0; i < 20; i++) source.add(i);
        List<Integer> dest = new ArrayList<>();

        // 2. Initialize Buffer with the dynamic capacity
        SharedBuffer<Integer> buffer = new CustomBlockingBuffer<>(capacity);

        // 3. Execution (Start Threads)
        Thread t1 = new Thread(new Producer(buffer, source));
        Thread t2 = new Thread(new Consumer(buffer, dest));

        t1.start();
        t2.start();

        // 4. Wait for threads to complete
        t1.join();
        t2.join();

        // 5. Verification
        // If synchronization is correct, Source and Destination must be identical.
        assertEquals(source, dest, "Data mismatch or loss detected for capacity: " + capacity);
    }

    /**
     * REQUIREMENT: Blocking Queues & Wait/Notify Mechanism.
     * <p>
     * This test mathematically proves that the 'wait()' method is working.
     * It forces a thread to write to a full buffer and asserts that the thread
     * enters the WAITING state (instead of overwriting data or crashing).
     */
    @Test
    void testProducerBlocksWhenFull() throws InterruptedException {
        // 1. Create a tiny buffer (Capacity 1)
        SharedBuffer<Integer> buffer = new CustomBlockingBuffer<>(1);

        // 2. Fill it completely (Size becomes 1)
        buffer.produce(100);

        // 3. Launch a separate thread that tries to add a second item.
        // Since the buffer is full, this thread MUST block.
        Thread t = new Thread(() -> {
            try {
                buffer.produce(200);
            } catch (InterruptedException e) {
                // Ignore interruption (expected during test cleanup)
            }
        });

        t.start();

        // 4. Give the thread a moment (100ms) to run and hit the "wait()" line
        Thread.sleep(100);

        // 5. Verify State
        // The thread should be physically stuck in WAITING or TIMED_WAITING state.
        assertTrue(
                t.getState() == Thread.State.WAITING || t.getState() == Thread.State.TIMED_WAITING,
                "Thread did not block! Current state: " + t.getState()
        );

        // Cleanup: Interrupt the stuck thread so the test finishes gracefully
        t.interrupt();
    }
}