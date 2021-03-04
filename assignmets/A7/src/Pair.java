class Pair<A,B> {
    private final A a;
    private final B b;

    Pair (A a, B b) { this.a = a; this.b = b; }

    A getFirst () { return a; }
    B getSecond () { return b; }

    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        else {
            @SuppressWarnings("unchecked") Pair<A,B> that = (Pair<A,B>) o;
            return a.equals(that.a) && b.equals(that.b);
        }
    }

    public int hashCode() {
        return a.hashCode() + 17 * b.hashCode();
    }

    public String toString () {
        return "<" + a + "," + b + ">";
    }

}
