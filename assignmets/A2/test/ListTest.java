import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    List<Integer> ints0 =
            new EmptyL<>();

    List<Integer> ints =
            new NodeL<>(5,
                    new NodeL<>(4,
                            new NodeL<>(3,
                                    new NodeL<>(2,
                                            new NodeL<>(1,
                                                    new NodeL<>(0,
                                                            new EmptyL<>()))))));

    List<Integer> ints100 =
            new NodeL<>(5,
                    new NodeL<>(4,
                            new NodeL<>(3,
                                    new NodeL<>(100,
                                            new NodeL<>(2,
                                                    new NodeL<>(1,
                                                            new NodeL<>(0,
                                                                    new EmptyL<>())))))));

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

    List<Integer> intsSquared =
            new NodeL<>(25,
                    new NodeL<>(16,
                            new NodeL<>(9,
                                    new NodeL<>(4,
                                            new NodeL<>(1,
                                                    new NodeL<>(0,
                                                            new EmptyL<>()))))));

    List<Integer> intsEvensOdds =
            new NodeL<>(5,
                    new NodeL<>(3,
                            new NodeL<>(1,
                                    new NodeL<>(4,
                                            new NodeL<>(2,
                                                    new NodeL<>(0,
                                                            new EmptyL<>()))))));

    List<Integer> intsRev =
            new NodeL<>(0,
                    new NodeL<>(1,
                            new NodeL<>(2,
                                    new NodeL<>(3,
                                            new NodeL<>(4,
                                                    new NodeL<>(5,
                                                            new EmptyL<>()))))));

    List<String> Hello =
            new NodeL<>("H",
                    new NodeL<>("e",
                            new NodeL<>("l",
                                    new NodeL<>("l",
                                            new NodeL<>("o",
                                                    new NodeL<>("!",
                                                            new EmptyL<>()))))));

    List<String> Hi =
            new NodeL<>("H",
                    new NodeL<>("i",
                            new EmptyL<>()));

    @Test
    void lists() throws EmptyListE {

        assertEquals(ints, List.countdown(5));

        assertEquals(ints100, ints.insertAfter(3, 100));

        assertEquals(ints, ints100.removeFirst(100));

        assertEquals(3, ints.indexOf(2));





        assertEquals(intsEvens, ints.filter(n -> n%2==0));
        assertEquals(intsOdds, ints.filter(n -> n%2==1));

        assertEquals(intsSquared, ints.map(n -> n*n));

        assertEquals(15, ints.reduce(0, Integer::sum));

        assertEquals(intsEvensOdds, intsOdds.append(intsEvens));

        assertEquals(intsRev, ints.reverse());


//        System.out.println(new NodeL<>(new NodeL<>(2, new NodeL<>(1, new EmptyL<>())),
//                new NodeL<>(new NodeL<>(2, new EmptyL<>()),
//                        new NodeL<>(new NodeL<>(1, new EmptyL<>()),
//                                new NodeL<>(new EmptyL<>(), new EmptyL<>())))));

//        new NodeL<>(new NodeL<>(2, new NodeL<>(1, new EmptyL<>())),
//                new NodeL<>(new NodeL<>(2, new EmptyL<>()),
//                        new NodeL<>(new NodeL<>(1, new EmptyL<>()),
//                                new NodeL<>(new EmptyL<>(), new EmptyL<>()))));

        List<List<Integer>> evensPS = intsEvens.powerSet();
        System.out.println(evensPS);
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

    @Test
    void lengthTests() throws EmptyListE {

        assertEquals(0, ints0.length());
        assertEquals(6, Hello.length());
    }

    @Test
    void inListTests() throws EmptyListE {

        assertTrue(ints100.inList(100));
        assertTrue(Hello.inList("!"));
        assertFalse(intsEvensOdds.inList(15));
        assertFalse(Hello.inList("p"));
    }

    @Test
    void insertAfterTests() throws EmptyListE {

        assertEquals(
                new NodeL<>("H",
                        new NodeL<>("e",
                                new NodeL<>("l",
                                        new NodeL<>(" ",
                                                new NodeL<>("l",
                                                        new NodeL<>(" ",
                                                                new NodeL<>("o",
                                                                        new NodeL<>("!",
                                                                                new EmptyL<>())))))))),
                Hello.insertAfter("l", " "));
        assertEquals(
                new NodeL<>("H",
                        new NodeL<>("e",
                                new NodeL<>("l",
                                        new NodeL<>("l",
                                                new NodeL<>("o",
                                                        new NodeL<>("!",
                                                                        new EmptyL<>())))))),
                Hello.insertAfter("W", " "));
    }

    @Test
    void removeFirstTests() throws EmptyListE {

        assertEquals(
                new NodeL<>("H",
                        new NodeL<>("e",
                                new NodeL<>("l",
                                        new NodeL<>("l",
                                                new NodeL<>("o",
                                                        new NodeL<>("!",
                                                                new EmptyL<>())))))),
        Hello.removeFirst(" "));
        assertEquals(
                new NodeL<>("H",
                        new NodeL<>("e",
                                new NodeL<>("l",
                                        new NodeL<>("o",
                                                new NodeL<>("!",
                                                        new EmptyL<>()))))),
                Hello.removeFirst("l"));
        assertEquals(
                new NodeL<>(4,
                        new NodeL<>(3,
                                new NodeL<>(2,
                                        new NodeL<>(1,
                                                new NodeL<>(0,
                                                        new EmptyL<>()))))),
                ints.removeFirst(5));
    }

    @Test
    void indexOfTests() throws EmptyListE {

        assertEquals(2, Hello.indexOf("l"));
        assertThrows(EmptyListE.class, () -> Hello.indexOf(" "));
    }

    @Test
    void filterTests() throws EmptyListE {

        assertEquals( new NodeL<>("l", new NodeL<>("l", new EmptyL<>())),
                Hello.filter((s) -> s.equals("l")));
        assertEquals(
                new NodeL<>(4,
                        new NodeL<>(1,
                                new NodeL<>(0,
                                        new EmptyL<>()))),
                ints.filter((n) -> Math.sqrt(n) % 1 == 0));
    }

    @Test
    void mapTests() throws EmptyListE {

        // TODO
    }

    @Test
    void reduceTests() throws EmptyListE {

        assertEquals("Hello!", Hello.reduce("", String::concat ));
        assertEquals(6, Hello.reduce(0, (s, acc) -> s.length() + acc));
        assertEquals(0, intsRev.reduce(1, (n, acc) -> n*acc));
        assertEquals(1, intsOdds.reduce(1, (n, acc) -> n/acc));
        assertEquals(25, intsOdds.reduce(1, (n, acc) -> n*n));
    }

    @Test
    void appendTests() throws EmptyListE {

        // TODO
    }

    @Test
    void powerSetTests() throws EmptyListE {

//        List<List<String>> HiPS = Hi.powerSet();
//        List<List<String>> HelloPS = Hello.powerSet();
//
//
//        assertEquals(64, HelloPS.powerSet().length());
//
//        assertEquals(4, HiPS.powerSet().length());
//        assertTrue(HiPS.inList(new EmptyL<String>()));
//        assertTrue(HiPS.inList(new NodeL<>("H", new EmptyL<>())));
//        assertTrue(HiPS.inList(new NodeL<>("i", new EmptyL<>())));
//        assertTrue(HiPS.inList(new NodeL<>("H", new NodeL<>("I", new EmptyL<>()))));
    }

    @Test
    void reverseTests() throws EmptyListE {

        // TODO
    }
}