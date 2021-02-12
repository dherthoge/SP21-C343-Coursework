import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

// -------------------------------------------------------

class NotFoundE extends Exception {}

// -------------------------------------------------------

abstract class HashTable<K,V> {
    // the current capacity of the underlying array
    private int capacity;
    // the number of elements currently stored in the hashtable
    private int size;
    // the underlying array: each index of the array is either
    // Optional.empty(), or
    // Optional.of(new AbstractMap.SimpleImmutableEntry<>(key, value))
    private ArrayList<Optional<Map.Entry<K, V>>> slots;
    // collects the indices of deleted items
    private HashSet<Integer> deleted;
    // a function defined in subclasses that determines
    // the next offset in case of collisions:
    // we will only define linear and quadratic probing
    private Function<Integer, Integer> offset;

    HashTable() {
        this.capacity = 17;
        this.size = 0;
        this.slots = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) this.slots.add(i, Optional.empty());
        deleted = new HashSet<>();
    }

    // called from subclasses
    void setOffset(Function<Integer, Integer> offset) {
        this.offset = offset;
    }

    // Getters for debugging

    int getCapacity() {
        return capacity;
    }

    int getSize() {
        return size;
    }

    ArrayList<Optional<Map.Entry<K, V>>> getSlots() {
        return slots;
    }

    HashSet<Integer> getDeleted() { return deleted; }

    // use Java hashcode (wrapping around to make sure the index
    // remains in bound
    int hash(K key) {
        return key.hashCode() % capacity;
    }

    // in case of collisions we add to the current index the
    // offset calculated by the given function (linear
    // or quadratic); and of course we wrap around to make
    // sure we stay in bounds
    int nextHash(int index, int collision) {
        return (index + offset.apply(collision)) % capacity;
    }

    // -------------------------------------------------------

    // to insert, we enter a loop (helper recursive function
    // actually). We calculate the hash of the key giving us
    // the position where we should store the item in the
    // case of no collisions. We also start a counter of
    // collisions initialized to 0.
    void insert(K key, V value) {
        insert(key, value, hash(key), 0);
    }

    // this is the general case of an insertion after
    // some given number of collisions:
    // - first thing calculate the index 'h' at which we
    //   will attempt to store (use nextHash)
    // - if slot 'h' is not empty, make a recursive
    //   call incrementing the number of collisions
    // - otherwise, store the current key and value
    //   in the hashtable at index 'h'
    // - do not forget to increment size and to
    //   remove 'h' from the collection of deleted
    //   indices
    // - after a successful insertion add the following
    //     if (size > capacity / 2) rehash();
    //   this will force the array to grow as soon as
    //   it is half full
    void insert(K key, V value, int index, int collision) {
        // TODO
        // call nextHash() for the next hash
    }

    // -------------------------------------------------------

    // like the case for insert, we enter a loop
    // keeping track of the number of collisions
    void delete(K key) throws NotFoundE {
        delete(key, hash(key), 0);
    }

    // This is the general case for deleting an item after
    // a number of collisions. There are several scenarios
    // to keep track of:
    // - if the number of collisions = capacity, the item
    //   is not present; throw an exception
    //   Challenge: write a test case that forces this
    //   exception to be thrown
    // - calculate the index 'h' we will focus on nextHash
    // - if the collection of deleted items contains 'h'
    //   skip this iteration and make a recursive call
    //   after incrementing the number of collisions
    // - if slot 'h' is empty, the item is not present;
    //   throw an exception
    // - if slot 'h' contains some key that is different
    //   from the key we are looking for, skip this
    //   iteration and make a recursive call after
    //   incrementing the number of collisions
    // - otherwise, set slot 'h' to empty, decrement size,
    //   and add 'h' to the collection of deleted indices
    void delete(K key, int index, int collision) throws NotFoundE {
        // TODO
    }

    // -------------------------------------------------------

    // the process of searching is quite similar to the one
    // for deleting and is left for you as an exercise

    V search(K key) throws NotFoundE {
        return null; // TODO
    }

    V search(K key, int index, int collision) throws NotFoundE {
        return null; // TODO
    }

    // -------------------------------------------------------

    // to rehash, we will perform the following actions:
    // - calculate a new capacity as follows:
    //   BigInteger.valueOf(oldCapacity*2L).nextProbablePrime().intValue();
    //   this will ensure that capacity is always a prime number
    // - create a new array of the new capacity
    // - enter a loop that takes every item in the old array
    //   and inserts it in the new array
    // - replace the old array by the new one and clear the
    //   collection of deleted indices
    void rehash () {
        // TODO
        // have to duplicate the insert logic
    }

    // -------------------------------------------------------

    public String toString () {
        String result = "";
        for (int i = 0; i< capacity; i++) {
            result += "entry[" + i + "] = ";
            result += deleted.contains(i) ? "***" : slots.get(i).toString();
            result += "\n";
        }
        return result;
    }
}

// -------------------------------------------------------


class HashLinearProbing<K,V> extends HashTable<K,V> {
    HashLinearProbing() {

        super();
        setOffset(collision -> collision);
    }
}

class HashQuadProbing<K,V> extends HashTable<K,V> {
    HashQuadProbing () {

        super();
        setOffset(collision -> collision * collision);
    }
}

// -------------------------------------------------------
