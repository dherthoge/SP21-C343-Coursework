import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {

    @Test
    public void simpleLinear () {
        HashTable<Integer,String> ht = new HashLinearProbing<>();
        ht.insert(0,"lamb");
        ht.insert(1,"cat");
        ht.insert(2,"dog");
        ht.insert(3,"horse");
        ht.insert(4,"cow");
        ht.insert(5,"chicken");
        ht.insert(6,"monkey");

        assertEquals(7, ht.getSize());
        assertTrue(ht.getDeleted().isEmpty());

        ArrayList<Optional<Map.Entry<Integer,String>>> slots = ht.getSlots();
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(0,"lamb")),slots.get(0));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(1,"cat")),slots.get(1));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(2,"dog")),slots.get(2));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(3,"horse")),slots.get(3));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(4,"cow")),slots.get(4));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(5,"chicken")),slots.get(5));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(6,"monkey")),slots.get(6));
    }

    @Test
    public void simpleQuad () {
        HashTable<Integer,String> ht = new HashQuadProbing<>();
        ht.insert(0,"lamb");
        ht.insert(1,"cat");
        ht.insert(2,"dog");
        ht.insert(3,"horse");
        ht.insert(4,"cow");
        ht.insert(5,"chicken");
        ht.insert(6,"monkey");

        assertEquals(7, ht.getSize());
        assertTrue(ht.getDeleted().isEmpty());

        ArrayList<Optional<Map.Entry<Integer,String>>> slots = ht.getSlots();
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(0,"lamb")),slots.get(0));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(1,"cat")),slots.get(1));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(2,"dog")),slots.get(2));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(3,"horse")),slots.get(3));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(4,"cow")),slots.get(4));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(5,"chicken")),slots.get(5));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(6,"monkey")),slots.get(6));
    }


    @Test
    public void clashQuad () {
        HashTable<Integer,String> ht = new HashQuadProbing<>();
        ht.insert(0,"lamb");
        ht.insert(17,"cat");
        ht.insert(34,"dog");
        ht.insert(51,"horse");
        ht.insert(68,"cow");
        ht.insert(8, "fox");
        ht.insert(85,"tiger");

        assertEquals(7, ht.getSize());
        assertTrue(ht.getDeleted().isEmpty());

        ArrayList<Optional<Map.Entry<Integer,String>>> slots = ht.getSlots();
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(0,"lamb")),slots.get(0));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(17,"cat")),slots.get(1));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(34,"dog")),slots.get(4));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(51,"horse")),slots.get(9));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(8,"fox")),slots.get(8));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(85,"tiger")),slots.get(2));
    }

    @Test
    public void clashLinear () throws NotFoundE {
        HashTable<Integer,String> ht = new HashLinearProbing<>();

        assertThrows(NotFoundE.class, () -> ht.search(1000));

        ht.insert(0,"lamb");
        ht.insert(7,"cat");
        ht.insert(14,"dog");
        ht.insert(21,"horse");
        ht.insert(35,"chicken");
        ht.insert(42,"monkey");
        ht.insert(24,"cow");

        assertEquals("lamb", ht.search(0));
        assertEquals("cat", ht.search(7));
        assertEquals("dog", ht.search(14));
        assertEquals("horse", ht.search(21));
        assertEquals("chicken", ht.search(35));
        assertEquals("monkey", ht.search(42));
        assertEquals("cow", ht.search(24));

        assertThrows(NotFoundE.class, () -> ht.search(1000));

        ht.insert(49, "lion");

        ArrayList<Optional<Map.Entry<Integer,String>>> slots = ht.getSlots();
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(0,"lamb")),slots.get(0));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(35,"chicken")),slots.get(1));
        assertTrue(slots.get(2).isEmpty());
        assertTrue(slots.get(3).isEmpty());
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(21,"horse")),slots.get(4));
        assertTrue(slots.get(5).isEmpty());
        assertTrue(slots.get(6).isEmpty());
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(7,"cat")),slots.get(7));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(42,"monkey")),slots.get(8));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(24,"cow")),slots.get(9));
        assertTrue(slots.get(10).isEmpty());
        assertTrue(slots.get(11).isEmpty());
        assertTrue(slots.get(12).isEmpty());
        assertTrue(slots.get(13).isEmpty());
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(14,"dog")),slots.get(14));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(49,"lion")),slots.get(15));
        assertTrue(slots.get(16).isEmpty());
    }

    @Test
    public void deletes () throws NotFoundE {
        HashTable<Integer,String> ht = new HashLinearProbing<>();

        ht.insert(1,"cat");
        ht.insert(18,"dog");
        ht.insert(35,"horse");
        ht.insert(52,"cow");
        ht.insert(69,"chicken");
        ht.insert(86,"lion");
        ht.insert(103,"tiger");
        ht.insert(120,"cheetah");

        assertThrows(NotFoundE.class, () -> ht.delete(1000));
        ht.delete(18);
        ht.delete(69);

        assertThrows(NotFoundE.class, () -> ht.search(18));
        assertThrows(NotFoundE.class, () -> ht.search(69));
        assertTrue(ht.getDeleted().contains(2));
        assertTrue(ht.getDeleted().contains(5));
        assertTrue(ht.getSlots().get(5).isEmpty());
        assertTrue(ht.getSlots().get(2).isEmpty());

        assertEquals("cat", ht.search(1));
        assertEquals("horse", ht.search(35));
        assertEquals("cow", ht.search(52));
        assertEquals("lion", ht.search(86));
        assertEquals("tiger", ht.search(103));
        assertEquals("cheetah", ht.search(120));

        ht.insert(18, "fox");
        assertEquals(ht.getSlots().get(2).orElseThrow(NotFoundE::new).getValue(), "fox");
    }

    // TODO
    // your own test cases here
}
