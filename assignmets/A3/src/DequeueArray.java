import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

class NoSuchElementE extends Exception {}

public abstract class DequeueArray<E> {

    protected Optional<E>[] elements;
    protected int capacity, front, back, size;
    //
    // data stored in locations:
    // front+1, front+2, ... back-2, back-1 (all mod capacity)
    //
    // common cases:
    // front points to an empty location
    // back points to an empty location
    // adding to front decreases 'front' by 1
    // adding to back increases 'back' by 1
    // removing does the opposite
    //
    // |-------------------------|
    // | 4 5 6 _ _ _ _ _ _ 1 2 3 |
    // |-------------------------|
    //         /\        /\      /\
    //        back      front  capacity
    //

    @SuppressWarnings("unchecked")
    DequeueArray(int initialCapacity) {
        elements = (Optional<E>[]) Array.newInstance(Optional.class, initialCapacity);
        Arrays.fill(elements, Optional.empty());
        capacity = initialCapacity;
        front = capacity - 1;
        back = 0;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        elements = (Optional<E>[]) Array.newInstance(Optional.class, 1);
        Arrays.fill(elements, Optional.empty());
        capacity = 1;
        front = 0;
        back = 0;
        size = 0;
    }

    public int size() { return size; }

    // --- real work begins --- //

    /**
      * Modulates the pointers so they never lay
      * outside of the bounds of the dequeue.
      */
    private void modPointers() {

        // Modulation differs depending on if the value is negative and the value being
        // modulated*-1 is less than the value of the mod (i.e. -2%5 is 3 but java returns -2,
        // while -6%5 is 1 and java returns the correct value)
        this.front = this.front < 0 ? this.capacity - (this.front*-1) % this.capacity : this.front;
        this.front = this.front >= this.capacity? this.front % this.capacity : this.front;

        this.back = this.back < 0 ? this.capacity - (this.back*-1) % this.capacity : this.back;
        this.back = this.back >= this.capacity ? this.back % this.capacity : this.back;
    }

    /**
     * Adds the given elem to the front of the dequeue
     * If there is no room, grow the dequeue first
     */
    public void addFirst(E elem) {

        // Check for need to grow
        if (this.size == this.capacity) this.grow();

        // Add the element after growth (if necessary) and update this.front and this.size
        this.elements[Math.floorMod(front,capacity)] = Optional.of(elem);
        this.front--;
        this.size++;

        this.modPointers();
    }

    /**
     * Adds the given elem to the back of the dequeue
     * If there is no room, grow the dequeue first
     */
    public void addLast(E elem) {

        // Check for need to grow
        if (this.size >= this.capacity) this.grow();

        // Add the element after growth (if necessary) and update this.front and this.size
        this.elements[Math.floorMod(back,capacity)] = Optional.of(elem);
        this.back++;
        this.size++;

        this.modPointers();
    }

    public E getFirst() throws NoSuchElementE {

        return elements[Math.floorMod(front+1,capacity)].orElseThrow(NoSuchElementE::new);
    }

    public E getLast() throws NoSuchElementE {

        return elements[Math.floorMod(back-1,capacity)].orElseThrow(NoSuchElementE::new);
    }

    /**
     * Removes (and returns) the first element in the dequeue
     * If the dequeue is empty, throw an exception instead
     */
    public E removeFirst() throws NoSuchElementE {
        E returnE = this.getFirst();
        this.elements[Math.floorMod(front+1,capacity)] = Optional.empty();
        front++;
        this.size--;
        this.modPointers();
        return returnE;
    }

    /**
     * Removes (and returns) the last element in the dequeue
     * If the dequeue is empty, throw an exception instead
     */
    public E removeLast() throws NoSuchElementE {
        E returnElem = this.getLast(); //
        this.elements[Math.floorMod(back-1,capacity)] = Optional.empty();
        back--;
        this.size--;
        this.modPointers();
        return returnElem;
    }

    // the following methods are for debugging and testing

    Optional<E>[] getElements () { return elements; }

    int getCapacity () { return capacity; }

    int getFront () { return front; }

    int getBack () { return back; }

    @Override
    public String toString() {

        String strRep = "[";
        for (Optional elem: elements) {

            try {
                strRep += elem.orElseThrow(NoSuchElementE::new) +
                        ", ";
            }
            catch (Throwable e) {
                strRep += "e, ";
            }
        }
        strRep = strRep.substring(0, strRep.length()-2) + "]";

        return strRep;
    }

    abstract void grow ();
}

// ---------------------------------------------------------

class DequeueArrayDouble<E> extends DequeueArray<E> {

    DequeueArrayDouble (int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Grow the dequeue by doubling its size.
     * A new array is created, all the elements in the old array
     * are copied in the first half of the new array
     */
    @SuppressWarnings("unchecked")
    void grow() {

        // Make a new array and fill with Optional.empty()
        Optional<E>[] newElements = (Optional<E>[]) Array.newInstance(Optional.class,
                this.getCapacity() * 2);
        Arrays.fill(newElements, Optional.empty());


        // Transfer old contents
        int numElemToTrans = this.size();
        for (int i = 0; i < numElemToTrans; i++){

            try {
                newElements[i] = Optional.of(this.removeFirst());
            } catch (NoSuchElementE noSuchElementE) {}
        }

        // Update elements trackers after resize (size stays the same)
        this.elements = newElements;
        this.capacity = this.getCapacity() * 2;
        this.front = this.capacity - 1;
        this.size = numElemToTrans;
        this.back = this.size();
    }
}

// ---------------------------------------------------------

class DequeueArrayOneAndHalf<E> extends DequeueArray<E> {

    DequeueArrayOneAndHalf (int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Grow the dequeue by multiplying its size by 1.5 and rounding
     * A new array is created, all the elements in the old array
     * are copied in the first half of the new array
     */
    @SuppressWarnings("unchecked")
    void grow() {

        // Make a new array and fill with Optional.empty()
        Optional<E>[] newElements = (Optional<E>[]) Array.newInstance(Optional.class,
                (int) Math.ceil((this.getCapacity() * 1.5)));
        Arrays.fill(newElements, Optional.empty());


        // Transfer old contents
        int numElemToTrans = this.size();
        for (int i = 0; i < numElemToTrans; i++){

            try {
                newElements[i] = Optional.of(this.removeFirst());
            } catch (NoSuchElementE noSuchElementE) {}
        }


        // Update elements trackers after resize (size stays the same)
        this.elements = newElements;
        this.capacity = (int) Math.ceil((this.getCapacity() * 1.5));
        this.front = this.capacity - 1;
        this.size = numElemToTrans;
        this.back = this.size();
    }
}

// ---------------------------------------------------------

class DequeueArrayPlusOne<E> extends DequeueArray<E> {

    DequeueArrayPlusOne (int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Grow the dequeue by one
     * A new array is created, all the elements in the old array
     * are copied in the first part of the new array
     */
    @SuppressWarnings("unchecked")
    void grow() {

        // Make a new array and fill with Optional.empty()
        Optional<E>[] newElements = (Optional<E>[]) Array.newInstance(Optional.class,
                this.getCapacity() + 1);
        Arrays.fill(newElements, Optional.empty());


        // Transfer old contents
        int numElemToTrans = this.size();
        for (int i = 0; i < numElemToTrans; i++){

            try {
                newElements[i] = Optional.of(this.removeFirst());
            } catch (NoSuchElementE noSuchElementE) {}
        }


        // Update elements trackers after resize (size stays the same)
        this.elements = newElements;
        this.capacity++;
        this.front = this.capacity - 1;
        this.size = numElemToTrans;
        this.back = this.capacity - 1;
    }
}
