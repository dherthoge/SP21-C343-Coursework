public class Pair<A, B> {
    private A fst;
    private B snd;

    Pair (A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }

    A getFirst () { return fst; }

    B getSecond () { return snd; }
}
