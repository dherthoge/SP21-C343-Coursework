import java.util.function.Function;

class Node implements Comparable<Node> {

    // Good
    static Node min (Node a, Node b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    private final String name;
    private int value;
    private boolean visited;
    private Heap heap;
    private int heapIndex;

    Node(String name) {
        this.name = name;
        this.visited = false;
    }

    // Good
    String getName() {
        return name;
    }

    // Good
    void setVisited() {
        this.visited = true;
    }

    // Good
    boolean isNotVisited() {
        return !visited;
    }

    // Good
    void setValue (int value) {
        this.value = value;
        if (heap != null) {

            heap.moveDown(this);
            heap.moveUp(this);
        }
    }

    // Good
    int getValue () {
        return value;
    }

    // Good
    void updateValue (Function<Integer,Integer> f) {

        this.value = f.apply(value);
        heap.moveDown(this);
        heap.moveUp(this);
    }

    // Good
    void setHeap (Heap heap) {
        this.heap = heap;
    }

    // Good
    void setHeapIndex (int heapIndex) { this.heapIndex = heapIndex; }

    // Good
    int getHeapIndex () { return heapIndex; }

    // Good
    public int compareTo(Node o) {
        return Integer.compare(value, o.value);
    }

    // Good
    public String toString() {
        return name;
    }

    // Good
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node that = (Node) o;
            return name.equals(that.getName());
        } else return false;
    }

    // Good
    public int hashCode() {
        return name.hashCode();
    }
}