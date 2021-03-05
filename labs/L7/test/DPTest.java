import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

// TODO write more tests!
public class DPTest {

    // Timers

    static <A, B> long time1(Function<A, B> f, A a) {
        long start = System.nanoTime();
        f.apply(a);
        long end = System.nanoTime();
        return (end - start) / 1000000;
    }

    static <A, B, C> long time2(BiFunction<A, B, C> f, A a, B b) {
        long start = System.nanoTime();
        f.apply(a, b);
        long end = System.nanoTime();
        return (end - start) / 1000000;
    }

    // Testing partition correctness and timing

    @Test
    public void bupartitionCorrectness() {
        List<Integer> ns = new Node<>(5, new Node<>(3,
                new Node<>(7, new Node<>(1, new Empty<>()))));
        assertFalse(DP.bupartition(ns, 2));
        System.out.println("new");
        assertTrue(DP.bupartition(ns, 8));
        System.out.println("new");
        assertTrue(DP.bupartition(ns, 6));
    }

    @Test
    public void partitionTiming() {
        Random r = new Random(1);
        List<Integer> s = List.MakeIntList(r, 100, 1000);
        long t = time2(DP::bupartition, s, 50000);
        assertTrue(t < 799);
    }

    // minDistance

    @Test
    public void buminDistance() {
        List<DP.BASE> dna1 =
                new Node<>(DP.BASE.A, new Node<>(DP.BASE.C, new Empty<>()));
        List<DP.BASE> dna2 =
                new Node<>(DP.BASE.A, new Node<>(DP.BASE.G, new Empty<>()));
        assertEquals(1, DP.buminDistance(dna1, dna2));

        List<DP.BASE> dna3 =
                new Node<>(DP.BASE.T, new Node<>(DP.BASE.A, new Node<>(DP.BASE.C, new Empty<>())));
        List<DP.BASE> dna4 =
                new Node<>(DP.BASE.A, new Node<>(DP.BASE.G, new Empty<>()));
        assertEquals(3, DP.buminDistance(dna3, dna4));
    }

    @Test
    public void minDistance2() {
        Random r = new Random(1);
        Function<Void, DP.BASE> g = v -> DP.BASE.class.getEnumConstants()[r.nextInt(4)];
        List<DP.BASE> dna1 = List.MakeList(g, 521);
        List<DP.BASE> dna2 = List.MakeList(g, 450);
        assertEquals(337, DP.buminDistance(dna1, dna2));
    }
}