import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class CycleDetectionTest {
        @Test
        public void cycle () {
                // TODO write your tests here. below is just one example of a linked list with a cycle
                // you should come up with your own examples
                List<Integer> lasteights = new NodeL<>(8, new EmptyL<>());
                List<Integer> lastthrees = new NodeL<>(3,
                                                new NodeL<>(4,
                                                        new NodeL<>(8,
                                                                new NodeL<>(5,
                                                                        new NodeL<>(8,
                                                                                new NodeL<>(6,
                                                                                        new NodeL<>(8,
                                                                                                new NodeL<>(7,
                                                                                                        new NodeL<>(8,
                                                                                                                lasteights)))))))));
                lasteights.setRest(lastthrees);
                // below is the full list with the cycle
                List<Integer> ls = new NodeL<>(1,
                        new NodeL<>(2,
                                lastthrees));

                assertTrue(List.cycleStart(ls).equals(Optional.of(3)));




                List<Integer> list1 = new NodeL<>(3,
                        new NodeL<>(4,
                                new NodeL<>(5,
                                        new NodeL<>(6,
                                                new NodeL<>(7, new EmptyL<>())))));
                assertTrue(List.cycleStart(list1).equals(Optional.empty()));
        }
}
