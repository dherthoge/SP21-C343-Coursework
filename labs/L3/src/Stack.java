// Stream API
// https://docs.oracle.com/javase/10/docs/api/java/util/stream/Stream.html

class EmptyStackE extends Exception {}

abstract class Stack<E> {
    abstract boolean isEmpty (); // O(1)
    abstract int size(); // O(1)

    abstract Stack<E> push (E elem); // O(1)
    abstract Stack<E> pop() throws EmptyStackE; // O(1)
    abstract E getTop() throws EmptyStackE; // O(1)

    abstract Stack<E> addLast (E elem); // O(N)
    abstract E getLast() throws EmptyStackE; // O(N)
}

class EmptyS<E> extends Stack<E> {

    boolean isEmpty() { return true; }

    int size() { return 0; }

    Stack<E> push(E elem) {
        return new NonEmptyS<>(elem, this);
    }

    Stack<E> pop() throws EmptyStackE {
        throw new EmptyStackE();
    }

    E getTop() throws EmptyStackE {
        throw new EmptyStackE();
    }

    Stack<E> addLast(E elem) {
        return new NonEmptyS<>(elem, this);
    }

    E getLast() throws EmptyStackE {
        throw new EmptyStackE();
    }

    public String toString () {
        return "";
    }
}

class NonEmptyS<E> extends Stack<E> {
    private final E top;
    private final Stack<E> rest;
    private final int size;

    NonEmptyS (E top, Stack<E> rest) {
        this.top = top;
        this.rest = rest;
        this.size = rest.size() + 1;
    }

    boolean isEmpty() { return false; }

    int size() { return size; }

    Stack<E> push(E elem) {
        return new NonEmptyS<>(elem, this);
    }

    Stack<E> pop() { return rest; }

    E getTop() {
        return top;
    }

    Stack<E> addLast(E elem) {
        return new NonEmptyS<>(top, rest.addLast(elem));
    }

    E getLast() throws EmptyStackE {
        if (rest.isEmpty()) {
            return top;
        }
        else {
            return rest.getLast();
        }
    }

    public String toString () {
        return String.format("%s; %s", top, rest.toString());
    }
}