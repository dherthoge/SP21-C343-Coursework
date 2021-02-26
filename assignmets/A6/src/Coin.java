public class Coin {
    private final int value;

    Coin (int value) {
        this.value = value;
    }

    int getValue () { return value; }

    public boolean equals(Object o) {
        if (!(o instanceof Coin)) return false;
        else {
            Coin that = (Coin) o;
            return value == that.value;
        }
    }

    public int hashCode() {
        return value;
    }

    public String toString () {
        return "@" + value;
    }

}
