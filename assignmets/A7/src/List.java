import java.util.function.BiFunction;

class EmptyListE extends Exception {
}

abstract class List<E> {
    abstract E getFirst() throws EmptyListE;

    abstract List<E> getRest() throws EmptyListE;

    abstract boolean isEmpty();

    abstract int length();

    abstract <R> R reduce (R base, BiFunction<E,R,R> acc);

    static <E> List<E> singleton (E e) {
        return new Node<>(e, new Empty<>());
    }
}

class Empty<E> extends List<E> {
    E getFirst() throws EmptyListE {
        throw new EmptyListE();
    }

    List<E> getRest() throws EmptyListE {
        throw new EmptyListE();
    }

    boolean isEmpty() {
        return true;
    }

    int length() {
        return 0;
    }

    <R> R reduce(R base, BiFunction<E, R, R> acc) {
        return base;
    }

    public boolean equals(Object o) {
        return o instanceof Empty;
    }

    public int hashCode() {
        return 7;
    }

    public String toString() {
        return "_";
    }
}

class Node<E> extends List<E> {
    private final E data;
    private final List<E> rest;
    private final int hash;

    Node(E data, List<E> rest) {
        this.data = data;
        this.rest = rest;
        hash = data.hashCode() + 31 * rest.hashCode();
    }

    E getFirst() {
        return data;
    }

    List<E> getRest() {
        return rest;
    }

    boolean isEmpty() {
        return false;
    }

    int length() {
        return 1 + rest.length();
    }

    <R> R reduce(R base, BiFunction<E, R, R> acc) {
        return acc.apply(data,rest.reduce(base,acc));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node)) return false;
        else {
            Node<E> that = (Node<E>) o;
            return data.equals(that.data) && rest.equals(that.rest);
        }
    }

    public int hashCode() {
        return hash;
    }

    public String toString() {
        return data + "," + rest;
    }
}

