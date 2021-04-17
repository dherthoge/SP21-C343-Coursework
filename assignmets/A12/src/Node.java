import java.util.ArrayList;
import java.util.function.Function;

class Node implements Comparable<Node> {
    static Node min (Node a, Node b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    private final String name;
    private int value;
    private boolean visited;
    private Heap heap;
    private int heapIndex;
    private Edge previousEdge;

    Node(String name) {
        this.name = name;
        this.visited = false;
    }

    String getName() {
        return name;
    }

    void setVisited() { this.visited = true; }

    boolean isNotVisited() {
        return !visited;
    }

    void setValue (int value) { this.value = value; }

    int getValue () { return value; }

    void updateValue (Function<Integer,Integer> f) {
        this.value = f.apply(value);
    }

    void updateAndMoveUp (Function<Integer,Integer> f) {
        updateValue(f);
        heap.moveUp(this);
    }

    void setHeap (Heap heap) { this.heap = heap; }

    void setHeapIndex (int heapIndex) { this.heapIndex = heapIndex; }

    int getHeapIndex () { return heapIndex; }

    Edge getPreviousEdge () { return previousEdge; }

    void setPreviousEdge (Edge edge) { previousEdge = edge; }

    /**
     * Traces back the chain of previousEdges to compute the
     * path from the source to this node. If the path is
     * not empty, the last edge in the path will be the
     * current previousEdge
     */
    ArrayList<Edge> followPreviousEdge () {
        // TODO
        throw new Error("TODO");
    }

    public int compareTo(Node o) {
        return Integer.compare(value, o.value);
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node that = (Node) o;
            return name.equals(that.getName());
        } else return false;
    }

    public int hashCode() {
        return name.hashCode();
    }
}
