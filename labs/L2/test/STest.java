import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class STest {
    private LinkedList<Integer> xs, ys;
    private LinkedList<String> zs;

    @BeforeEach
    public void init () {
        xs = new LinkedList<>(Arrays.asList(1,2,3,4));
        ys = new LinkedList<>(Arrays.asList(0,2,4));
        zs = new LinkedList<>(Arrays.asList("abc", "DEFGHI", "jklmnopqrst", "UVWXYZ"));
    }

    @Test
    public void triplicate () {
        List<Integer> triples = S.triplicate(xs);
        assertEquals(1,triples.get(0));
        assertEquals(1,triples.get(1));
        assertEquals(1,triples.get(2));
        assertEquals(2,triples.get(3));
        assertEquals(2,triples.get(4));
        assertEquals(2,triples.get(5));
        assertEquals(3,triples.get(6));
        assertEquals(3,triples.get(7));
        assertEquals(3,triples.get(8));
        assertEquals(4,triples.get(9));
        assertEquals(4,triples.get(10));
        assertEquals(4,triples.get(11));
    }

    @Test
    public void square () {
        List<Integer> squares = S.square(xs);
        assertEquals(1,squares.get(0));
        assertEquals(4,squares.get(1));
        assertEquals(9,squares.get(2));
        assertEquals(16,squares.get(3));
    }

    @Test
    public void allEven () {
        assertFalse(S.allEven(xs));
        assertTrue(S.allEven(ys));
    }

    @Test
    public void evens () {
        List<Integer> evens = S.evens(xs);
        System.out.printf("xs: %s%n", evens);
        assertEquals(2,evens.get(0));
        assertEquals(4,evens.get(1));
    }

    @Test
    public void mul () {
        assertEquals(24,S.mul(xs));
        assertEquals(0,S.mul(ys));
    }

    @Test
    public void checksum () {
        assertEquals(0,S.checksum(xs));
        assertEquals(6, S.checksum(ys));
    }

    @Test
    public void lengths () {
        assertEquals(26,S.lengths(zs));
    }

    @Test
    public void largest () {
        assertEquals("UVWXYZ", S.largest(zs));
    }
}