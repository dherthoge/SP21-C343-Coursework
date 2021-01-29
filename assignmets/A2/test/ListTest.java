import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    @Test
    void lists() throws EmptyListE {
        List<Integer> ints =
                new NodeL<>(5,
                        new NodeL<>(4,
                                new NodeL<>(3,
                                        new NodeL<>(2,
                                                new NodeL<>(1,
                                                        new NodeL<>(0,
                                                                new EmptyL<>()))))));
        assertEquals(ints, List.countdown(5));

        List<Integer> ints100 =
                new NodeL<>(5,
                        new NodeL<>(4,
                                new NodeL<>(3,
                                        new NodeL<>(100,
                                                new NodeL<>(2,
                                                        new NodeL<>(1,
                                                                new NodeL<>(0,
                                                                        new EmptyL<>())))))));

        assertEquals(ints100, ints.insertAfter(3, 100));

        assertEquals(ints, ints100.removeFirst(100));

        assertEquals(3, ints.indexOf(2));

        List<Integer> intsEvens =
                new NodeL<>(4,
                        new NodeL<>(2,
                                new NodeL<>(0,
                                        new EmptyL<>())));

        List<Integer> intsOdds =
                new NodeL<>(5,
                        new NodeL<>(3,
                                new NodeL<>(1,
                                        new EmptyL<>())));

        assertEquals(intsEvens, ints.filter(n -> n%2==0));
        assertEquals(intsOdds, ints.filter(n -> n%2==1));

        List<Integer> intsSquared =
                new NodeL<>(25,
                        new NodeL<>(16,
                                new NodeL<>(9,
                                        new NodeL<>(4,
                                                new NodeL<>(1,
                                                        new NodeL<>(0,
                                                                new EmptyL<>()))))));

        assertEquals(intsSquared, ints.map(n -> n*n));

//        assertEquals(15, ints.reduce(0, Integer::sum));

        List<Integer> intsEvensOdds =
                new NodeL<>(5,
                        new NodeL<>(3,
                                new NodeL<>(1,
                                        new NodeL<>(4,
                                                new NodeL<>(2,
                                                        new NodeL<>(0,
                                                                new EmptyL<>()))))));

        assertEquals(intsEvensOdds, intsOdds.append(intsEvens));

        List<Integer> intsRev =
                new NodeL<>(0,
                        new NodeL<>(1,
                                new NodeL<>(2,
                                        new NodeL<>(3,
                                                new NodeL<>(4,
                                                        new NodeL<>(5,
                                                                new EmptyL<>()))))));

        assertEquals(intsRev, ints.reverse());

        List<List<Integer>> evensPS = intsEvens.powerSet();

        assertEquals(8, evensPS.length());
        assertTrue(evensPS.inList(new EmptyL<>()));
        assertTrue(evensPS.inList(new NodeL<>(0, new EmptyL<>())));
        assertTrue(evensPS.inList(new NodeL<>(2, new EmptyL<>())));
        assertTrue(evensPS.inList(new NodeL<>(4, new EmptyL<>())));
        assertTrue(evensPS.inList(new NodeL<>(4, new NodeL<>(2, new EmptyL<>()))));
        assertTrue(evensPS.inList(new NodeL<>(4, new NodeL<>(0, new EmptyL<>()))));
        assertTrue(evensPS.inList(new NodeL<>(2, new NodeL<>(0, new EmptyL<>()))));
        assertTrue(evensPS.inList(new NodeL<>(4, new NodeL<>(2, new NodeL<>(0, new EmptyL<>())))));
    }
}