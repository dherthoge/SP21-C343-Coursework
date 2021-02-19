public class Pair<A,B> {
    private final A fst;
    private final B snd;

    Pair (A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }

    A getFst () { return fst; }

    B getSnd () { return snd; }
}
