import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    @Test
    void queues () throws EmptyQueueE {
        Queue<Integer> q1 = new SlowQueue<>();
        for (int i=0; i<10; i++) {
            q1.enqueue(i);
        }
        for (int i=0; i<10; i++) {
            assertEquals(i, q1.getFront());
            q1.dequeue();
        }

        Queue<Integer> q2 = new AmortizedQueue<>();
        for (int i=0; i<10; i++) {
            q2.enqueue(i);
        }
        for (int i=0; i<10; i++) {
            assertEquals(i, q2.getFront());
            q2.dequeue();
        }
    }

    Duration timeQueue (Random r, int size, int bound, Queue<Integer> q) throws EmptyQueueE {

        Instant start = Instant.now();

        for (int i=0; i<100; i++) {
            q.enqueue(r.nextInt(bound));
        }

        for (int i=0; i<size; i++) {
            if (r.nextBoolean()) {
                int e = r.nextInt(bound);
                q.enqueue(e);
            }
            else {
                q.dequeue();
            }
        }

        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    @Test
    void timeQueues () throws EmptyQueueE {
        Random r = new Random(100);
        int size = 10000;
        int bound = 1000;

        Duration d1 = timeQueue(r, size, bound, new SlowQueue<>());
        System.out.printf("Running %d enqueue/dequeue operations on slow queue; time in ms = %d%n",
                size, d1.toMillis());

        Duration d2 = timeQueue(r, size, bound, new AmortizedQueue<>());
        System.out.printf("Running %d enqueue/dequeue operations on amortized queue; time in ms = %d%n",
                size, d2.toMillis());
    }


    // My tests

    @Test
    void mySlowQueueTests() throws EmptyQueueE {
        Queue<String> q1 = new SlowQueue<>();
        q1.enqueue("hi");
        q1.enqueue("bye");

        assertEquals("hi", q1.getFront());
        q1.dequeue();
        q1.enqueue("see ya");
        assertEquals("bye", q1.getFront());
        assertEquals("bye", q1.getFront());
        q1.dequeue();
        assertEquals("see ya", q1.getFront());
        q1.dequeue();
        assertThrows(EmptyQueueE.class, () -> q1.dequeue());
        assertThrows(EmptyQueueE.class, () -> q1.getFront());
    }

    @Test
    void myAmortizedQueueTests() throws EmptyQueueE {

        Queue<String> q1 = new AmortizedQueue<>();
        q1.enqueue("hi");
        q1.enqueue("bye");

        assertEquals("hi", q1.getFront());
        q1.enqueue("see ya");
        q1.dequeue();
        assertEquals("bye", q1.getFront());
        assertEquals("bye", q1.getFront());
        q1.dequeue();
        assertEquals("see ya", q1.getFront());
        q1.dequeue();
        assertThrows(EmptyQueueE.class, () -> q1.dequeue());
        assertThrows(EmptyQueueE.class, () -> q1.getFront());

        // Testing transferElem method
        AmortizedQueue<String> q2 = new AmortizedQueue<>();

        assertThrows(EmptyStackE.class, () -> q2.transferElems());
        q2.enqueue("1");
        q2.enqueue("2");
        q2.enqueue("3");
        assertEquals("1", q2.getFront());
        q2.dequeue();
        q2.enqueue("4");
        assertEquals("2", q2.getFront());
        q2.dequeue();
        assertEquals("3", q2.getFront());
        q2.dequeue();
        assertEquals("4", q2.getFront());
        q2.dequeue();
        assertThrows(EmptyStackE.class, () -> q2.transferElems());
    }

    Duration myTimeQueue (Random r, int size, int bound, Queue<Integer> q) throws EmptyQueueE {

        Instant start = Instant.now();

        for (int i=0; i<100; i++) {
            q.enqueue(r.nextInt(bound));
        }

        for (int i=0; i<size; i++) {
            if (r.nextBoolean()) {
                int e = r.nextInt(bound);
                q.enqueue(e);
            }
            else {
                try {
                    q.dequeue();
                } catch (EmptyQueueE e) {

                }
            }
        }

        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    @Test
    void myTimeQueues () throws EmptyQueueE {
        Random r = new Random(100);
        int size = 200000;
        int bound = 1000;

        Duration d1 = myTimeQueue(r, size, bound, new SlowQueue<>());
        System.out.printf("Running %d enqueue/dequeue operations on slow queue; time in ms = %d%n",
                size, d1.toMillis());

        Duration d2 = myTimeQueue(r, size, bound, new AmortizedQueue<>());
        System.out.printf("Running %d enqueue/dequeue operations on amortized queue; time in ms = %d%n",
                size, d2.toMillis());
    }

}