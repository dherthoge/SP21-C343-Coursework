import java.util.Random;
import java.util.function.Function;

class EmptyListE extends Exception {}

abstract class List<E> {
    static <A> List<A> MakeList (Function<Void,A> g, int size) {
        List<A> result = new Empty<>();
        for (int i = 0; i < size; i++) {
            result = new Node<>(g.apply(null), result);
        }
        return result;
    }
    static List<Integer> MakeIntList (Random r, int bound, int size) {
        return MakeList((v) -> r.nextInt(bound), size);
    }

    List<E> push (E elem) { return new Node<>(elem, this); }

    abstract E getFirst () throws EmptyListE;
    abstract List<E> getRest () throws EmptyListE;
    abstract E get (int i) throws EmptyListE;
    abstract boolean isEmpty ();
    abstract int length();
    abstract List<E> add (E elem);
    abstract List<E> reverse ();
    abstract List<E> append (List<E> other);
    abstract <R> List<R> map (Function<E,R> f);
    abstract List<E> insertByKey (E elem, Function<E,Double> key);
    abstract List<E> sort (Function<E,Double> key);
    abstract List<E> removeDups();
    abstract boolean contains (E e);
    abstract List<E> drop (int i);
    abstract void forEach (Function<E,Void> f);
}

class Empty<E> extends List<E> {
    E getFirst () throws EmptyListE { throw new EmptyListE(); }
    List<E> getRest () throws EmptyListE { throw new EmptyListE(); }
    E get (int i) throws EmptyListE { throw new EmptyListE(); }
    boolean isEmpty () { return true; }
    int length() { return 0; }
    List<E> add (E elem) { return new Node<>(elem, this); }
    List<E> reverse () { return this; }
    List<E> append (List<E> other) { return other; }
    <R> List<R> map (Function<E,R> f) { return new Empty<>(); }
    List<E> insertByKey (E elem, Function<E,Double> key) {
        return new Node<>(elem, new Empty<>());
    }
    List<E> sort (Function<E,Double> key) { return this; }
    List<E> removeDups () { return this; }
    boolean contains (E e) { return false; }
    List<E> drop (int i) { return this; }
    void forEach (Function<E,Void> f) {}

    public boolean equals(Object o) { return o instanceof Empty; }
    public int hashCode() { return 0; }
    public String toString () { return "#"; }
}

class Node<E> extends List<E> {
    private final E data;
    private final List<E> rest;

    Node (E data, List<E> rest) {
        this.data = data;
        this.rest = rest;
    }

    E getFirst () { return data; }
    List<E> getRest () { return rest; }
    E get (int i) throws EmptyListE {
        if (i == 0) return data;
        else return rest.get(i-1);
    }
    boolean isEmpty () { return false; }
    int length() { return 1 + rest.length(); }
    List<E> add (E elem) { return new Node<>(data, rest.add(elem)); }
    List<E> reverse () { return rest.reverse().add(data); }
    List<E> append (List<E> other) {
        return new Node<>(data, rest.append(other));
    }
    <R> List<R> map (Function<E,R> f) {
        return new Node<>(f.apply(data), rest.map(f));
    }
    List<E> insertByKey (E elem, Function<E,Double> key) {
        if (key.apply(elem) < key.apply(data)) {
            return new Node<>(elem, this);
        }
        else {
            return new Node<>(data, rest.insertByKey(elem,key));
        }
    }
    List<E> sort (Function<E,Double> key) {
        return rest.sort(key).insertByKey(data,key);
    }
    List<E> removeDups () {
        if (rest.contains(data)) return rest.removeDups();
        else return new Node<>(data, rest.removeDups());
    }
    boolean contains (E e) {
        return data.equals(e) || rest.contains(e);
    }
    List<E> drop (int i) {
        if (i == 0) return this;
        else return rest.drop(i-1);
    }
    void forEach (Function<E,Void> f) {
        f.apply(data);
        rest.forEach(f);
    }


    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof Node)) return false;
        else {
            Node<E> that = (Node<E>) o;
            return data.equals(that.data) && rest.equals(that.rest);
        }
    }
    public int hashCode() { return 7 * data.hashCode() + 17 * rest.hashCode(); }
    public String toString () { return data + "," + rest; }
}
