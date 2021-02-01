import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

class EmptyListE extends Exception {}

//-------------------------------------------------------------

/**
 * This is persistent implementation.
 * After a list is created, it is never updated
 *
 * See the test cases for examples
 */
abstract class List<E> {
    static List<Integer> countdown (int n) {
        if (n == 0)
            return new NodeL<>(0, new EmptyL<>());
        else
            return new NodeL<>(n, countdown(n-1));
    }

    /**
     * Computes the length of the list
     */
    abstract int length();

    /**
     * Checks if the given elem occurs in the list
     * (Use .equals() to check for equality)
     */
     boolean inList(E elem) {
         return this.reduce(false, (a, e) -> a.equals(elem) || e);
     };

    /**
     * Inserts newElem after every occurrence of elem
     */
    abstract List<E> insertAfter (E elem, E newElem);

    /**
     * Removes the first occurrence of elem (if
     * there is one)
     */
    abstract List<E> removeFirst (E elem);

    /**
     * Returns the 0-based index of elem in the list
     * If the element is not in the list, throw
     * an exception
     */
    abstract int indexOf (E elem) throws EmptyListE;

    /**
     * Returns a new list that only contains the
     * elements satisfying the given predicate
     */
    abstract List<E> filter (Function<E,Boolean> pred);

    /**
     * Returns a new list in which every element
     * is transformed using the given function
     */
    abstract <F> List<F> map (Function<E,F> f);

    /**
     * Starting with base as the current result,
     * repeatedly applies the bifunction f to the
     * current result and one element from the list
     */
    abstract <F> F reduce(F base, BiFunction<E,F,F> f);

    /**
     * Appends the given list at the end of the
     * current list
     */
    abstract List<E> append (List<E> other);

    /**
     * Use to accumulate the reverse of the given list
     */
    abstract List<E> reverseHelper (List<E> result);

    /**
     * Returns a big list containing all the sublists of
     * the current list
     */
    abstract List<List<E>> powerSet ();

    List<E> reverse () { return this.reverseHelper(new EmptyL<>()); }
}

//-------------------------------------------------------------

class EmptyL<E> extends List<E> {

    int length() {
        return 0;
    }

    List<E> insertAfter(E elem, E newElem) { return this; }

    List<E> removeFirst(E elem) { return this; }

    int indexOf(E elem) throws EmptyListE { throw new EmptyListE(); }

    List<E> filter(Function<E, Boolean> pred) { return this; }

    <F> List<F> map(Function<E, F> f) { return new EmptyL<>(); }

    <F> F reduce(F base, BiFunction<E, F, F> f) { return base; }

    List<E> append(List<E> other) { return other; }

    List<E> reverseHelper(List<E> result) { return result; }

    List<List<E>> powerSet() {
        return new NodeL<>(new EmptyL<>(), new EmptyL<>());
    }

    public boolean equals (Object o) {
        return o instanceof EmptyL;
    }

    public String toString() {
        return "e";
    }
}

//-------------------------------------------------------------

class NodeL<E> extends List<E> {

    private final E first;
    private final List<E> rest;

    NodeL (E first, List<E> rest) {
        this.first = first;
        this.rest = rest;
    }

    int length() { return 1 + this.rest.length(); }

    List<E> insertAfter(E elem, E newElem) {

        if (this.first == elem) {
            return new NodeL<>(this.first, new NodeL<>(newElem, this.rest.insertAfter(elem,
                    newElem)));
        }
        else {
            return new NodeL<>(this.first, this.rest.insertAfter(elem, newElem));
        }
    }

    List<E> removeFirst(E elem) {

        if (this.first == elem) {
            return this.rest;
        }
        else {
            return new NodeL<>(this.first, this.rest.removeFirst(elem));
        }
    }

    int indexOf(E elem) throws EmptyListE {

        if (this.first == elem) {
            return 0;
        }
        else {
            return 1 + this.rest.indexOf(elem);
        }
    }

    List<E> filter(Function<E, Boolean> pred) {

        if (pred.apply(this.first)) {
            return new NodeL<>(this.first, this.rest.filter(pred));
        }
        else {
            return this.rest.filter(pred);
        }
    }

    <F> List<F> map(Function<E, F> f) {

        return new NodeL<>(f.apply(this.first), this.rest.map(f));
    }

    <F> F reduce(F base, BiFunction<E, F, F> f) {

        return f.apply(this.first, this.rest.reduce(base, f));
    }

    List<E> append(List<E> other) { return new NodeL<>(this.first, this.rest.append(other)); }

    List<E> reverseHelper(List<E> result) { return this.rest.reverseHelper(new NodeL<>(this.first
            , result)); }

    List<List<E>> powerSet() {

        return new NodeL<>(new NodeL<>(this.first, this.rest),
                new NodeL<>(new NodeL<>(this.first, new EmptyL<>()),
                    this.rest.powerSet()));

        //return null; // TODO
    }

    public boolean equals (Object o) {
        if (o instanceof NodeL) {
            NodeL<E> other = (NodeL<E>) o;
            return first.equals(other.first) && rest.equals(other.rest);
        }
        else return false;
    }

    public String toString() { return first + ", " + rest.toString(); }
}

//-------------------------------------------------------------
//-------------------------------------------------------------

