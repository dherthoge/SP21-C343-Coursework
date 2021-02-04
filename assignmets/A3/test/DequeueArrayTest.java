import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DequeueArrayTest {

    @Test
    public void dequeDoubleNoResize() throws NoSuchElementE {
        DequeueArray<Integer> d = new DequeueArrayDouble<>(5);
        assertEquals(0, d.size());
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        assertEquals(3, d.removeFirst());
        assertEquals(1, d.removeLast());
        assertEquals(2, d.removeLast());
        assertEquals(0, d.size());
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);
        d.addFirst(5);
        assertEquals(5, d.removeFirst());
        assertEquals(3, d.removeFirst());
        assertEquals(1, d.removeFirst());
        assertEquals(2, d.removeFirst());
        assertEquals(4, d.removeFirst());
    }

    @Test
    public void dequeDoubleResize() throws NoSuchElementE {
        DequeueArray<Integer> d = new DequeueArrayDouble<>(5);
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);
        d.addFirst(5);
        d.addFirst(6);
        assertEquals(10, d.getCapacity());
        assertEquals(6, d.removeFirst());
        assertEquals(5, d.removeFirst());
        assertEquals(3, d.removeFirst());
        assertEquals(1, d.removeFirst());
        assertEquals(2, d.removeFirst());
        assertEquals(4, d.removeFirst());
    }

    Duration timeDequeue (Random r, int size, int bound, DequeueArray<Integer> dq) throws NoSuchElementE {
        Instant start = Instant.now();

        for (int i=0; i<1000; i++) {
            dq.addFirst(r.nextInt(bound));
        }

        for (int i=0; i<size; i++) {
            int e = r.nextInt(bound);
            switch (r.nextInt(4)) {
                case 0 -> dq.addFirst(e);
                case 1 -> dq.addLast(e);
                case 2 -> dq.removeFirst();
                case 3 -> dq.removeLast();
                default -> throw new Error("Impossible");
            }
        }

        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    @Test
    void timeDeQueues () throws NoSuchElementE {
        Random r = new Random(500);
        int size = 10000;
        int bound = 1000;

        Duration d1 = timeDequeue(r, size, bound, new DequeueArrayDouble<>(100));
        System.out.printf("Running %d operations (size *2); time in ms = %d%n",
                size, d1.toMillis());

        Duration d2 = timeDequeue(r, size, bound, new DequeueArrayOneAndHalf<>(100));
        System.out.printf("Running %d operations (size *1.5); time in ms = %d%n",
                size, d2.toMillis());

        Duration d3 = timeDequeue(r, size, bound, new DequeueArrayPlusOne<>(100));
        System.out.printf("Running %d operations (size +1); time in ms = %d%n",
                size, d3.toMillis());
    }

    // TODO test every method along with testing exception throwing

}

