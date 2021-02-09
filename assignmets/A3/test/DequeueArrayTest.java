import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
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


    // My tests

    @Test
    // Tests addFirst, removeFirst, addLast, and removeLast (also testing removeFirst and removeLast exceptions)
    // By testing removeFirst and removeLast, I'm testing getFirst and getLast indirectly
    void addRemoveTest() throws NoSuchElementE {

        DequeueArray<Integer> d = new DequeueArrayDouble<>(5);
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        assertEquals(3, d.removeFirst());
        assertEquals(2, d.removeFirst());
        assertEquals(1, d.removeFirst());
        assertThrows(NoSuchElementE.class, d::removeFirst);
        assertThrows(NoSuchElementE.class, d::removeLast);
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addFirst(4);
        d.addLast(5);
        d.addLast(6);
        assertEquals(6, d.removeLast());
        assertEquals(4, d.removeFirst());
        assertEquals(5, d.removeLast());
        assertEquals(3, d.removeFirst());
        assertEquals(1, d.removeLast());
        assertEquals(2, d.removeFirst());
    }

    @Test
    void growTest() throws NoSuchElementE {

        DequeueArray<Integer> dequeueDouble = new DequeueArrayDouble<>(2);
        dequeueDouble.addLast(1);
        assertEquals(2, dequeueDouble.getCapacity());
        dequeueDouble.addLast(2);
        assertEquals(2, dequeueDouble.getCapacity());
        dequeueDouble.addLast(3);
        assertEquals(4, dequeueDouble.getCapacity());
        dequeueDouble.addLast(4);
        assertEquals(4, dequeueDouble.getCapacity());
        dequeueDouble.addLast(5);
        assertEquals(8, dequeueDouble.getCapacity());
        dequeueDouble.addLast(6);
        assertEquals(8, dequeueDouble.getCapacity());
        dequeueDouble.removeFirst();


        DequeueArray<Integer> dequeueHalf = new DequeueArrayOneAndHalf<>(2);
        dequeueHalf.addLast(1);
        assertEquals(2, dequeueHalf.getCapacity());
        dequeueHalf.addLast(2);
        assertEquals(2, dequeueHalf.getCapacity());
        dequeueHalf.addLast(3);
        assertEquals(3, dequeueHalf.getCapacity());
        dequeueHalf.addLast(4);
        assertEquals(5, dequeueHalf.getCapacity());
        dequeueHalf.addLast(5);
        assertEquals(5, dequeueHalf.getCapacity());
        dequeueHalf.addLast(6);
        assertEquals(8, dequeueHalf.getCapacity());
        dequeueHalf.removeFirst();


        DequeueArray<Integer> dequeueOne = new DequeueArrayPlusOne<>(2);
        dequeueOne.addLast(1);
        assertEquals(2, dequeueOne.getCapacity());
        dequeueOne.addLast(2);
        assertEquals(2, dequeueOne.getCapacity());
        dequeueOne.addLast(3);
        assertEquals(3, dequeueOne.getCapacity());
        dequeueOne.addLast(4);
        assertEquals(4, dequeueOne.getCapacity());
        dequeueOne.addLast(5);
        assertEquals(5, dequeueOne.getCapacity());
        dequeueOne.addLast(6);
        assertEquals(6, dequeueOne.getCapacity());
        dequeueHalf.removeFirst();
    }

    @Test
    void modTest() throws NoSuchElementE {

        DequeueArray<Integer> dequeueDouble = new DequeueArrayDouble<>(5);
        dequeueDouble.addFirst(0);
        dequeueDouble.addFirst(4);
        dequeueDouble.addFirst(3);
        System.out.println(dequeueDouble);
        dequeueDouble.removeLast();
        dequeueDouble.removeLast();
        dequeueDouble.removeLast();
        System.out.println(dequeueDouble);
        assertEquals(2, dequeueDouble.getBack());
    }


    // Used for debugging autograder errors
    @Test
    void plusOneAddLastResizeTest() {

        DequeueArray<Integer> dequeueDouble = new DequeueArrayPlusOne<>(2);
        dequeueDouble.addFirst(1);
        dequeueDouble.addFirst(0);
        dequeueDouble.addLast(1);
        dequeueDouble.addLast(0);
    }

    @Test
    void oneAndAHalfTests() {

        DequeueArray<Integer> dequeueDouble = new DequeueArrayOneAndHalf<>(2);
        dequeueDouble.addFirst(0);
        dequeueDouble.addFirst(1);
        dequeueDouble.addLast(2);
        dequeueDouble.addLast(3);
        dequeueDouble.addFirst(4);
        dequeueDouble.addFirst(5);
        dequeueDouble.addLast(6);
        dequeueDouble.addLast(7);
        dequeueDouble.addFirst(8);
        dequeueDouble.addFirst(9);
        dequeueDouble.addLast(10);
        dequeueDouble.addLast(11);
        dequeueDouble.addLast(12);
    }

    @Test
    void removeFirstModTest() throws NoSuchElementE {

        DequeueArray<Integer> dequeueDouble = new DequeueArrayOneAndHalf<>(5);
        dequeueDouble.addLast(0);
        dequeueDouble.addLast(1);
        dequeueDouble.addLast(2);
        dequeueDouble.addLast(3);
        dequeueDouble.removeFirst();
        dequeueDouble.removeFirst();
        dequeueDouble.removeFirst();
        dequeueDouble.addLast(0);
        dequeueDouble.addLast(1);
        dequeueDouble.addLast(2);
        dequeueDouble.addLast(3);
    }
}

