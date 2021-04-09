import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

class Heap {
    private final ArrayList<Node> nodes;
    private int size;

    Heap (Set<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
        for (Node n : this.nodes) n.setHeap(this);
        this.size = nodes.size();
        heapify();
    }

    // Good
    void heapify () {

        for (int i=size/2-1; i>=0; i--) moveDown(nodes.get(i));
        for (int i = 0; i < this.nodes.size(); i++) this.nodes.get(i).setHeapIndex(i);
    }

    // Good
    boolean isEmpty () {
        return size == 0;
    }

    // Good
    Optional<Node> getParent(Node n) {
        int parentIndex = (n.getHeapIndex() - 1) / 2;
        if (parentIndex < 0) return Optional.empty();
        return Optional.of(nodes.get(parentIndex));
    }

    // Good
    Optional<Node> getLeftChild(Node n) {
        int childIndex = 2 * n.getHeapIndex() + 1;
        if (childIndex >= size) return Optional.empty();
        return Optional.of(nodes.get(childIndex));
    }

    // Good
    Optional<Node> getRightChild(Node n) {
        int childIndex = 2 * n.getHeapIndex() + 2;
        if (childIndex >= size) return Optional.empty();
        return Optional.of(nodes.get(childIndex));
    }

    // Good
    Optional<Node> getMinChild(Node n) {
        Optional<Node> leftChild = getLeftChild(n);
        Optional<Node> rightChild = getRightChild(n);
        if (rightChild.isEmpty()) return leftChild;
        else {
            assert leftChild.isPresent();
            return Optional.of(Node.min(leftChild.get(), rightChild.get()));
        }
    }

    // Good
    void swap(Node n1, Node n2) {
        int p1 = n1.getHeapIndex();
        int p2 = n2.getHeapIndex();
        n1.setHeapIndex(p2);
        n2.setHeapIndex(p1);
        nodes.set(p1, n2);
        nodes.set(p2, n1);
    }

    // Good
    void moveDown(Node n) {
        Optional<Node> minChild = getMinChild(n);
        minChild.ifPresent(c -> {
            if (c.compareTo(n) < 0) {
                swap(n, c);
                moveDown(n);
            }
        });
    }

    // Good
    void moveUp(Node n) {
        Optional<Node> parent = getParent(n);
        parent.ifPresent(p -> {
            if (n.compareTo(p) < 0) {
                swap(n, p);
                moveUp(n);
            }
        });
    }

    // Good
    void insert (Node n) {
        n.setHeapIndex(size);
        nodes.add(size, n); // was size++
        moveUp(n);
    }

    // Good
    Node extractMin() {

        size--;

        // If the heap only has 1 item remove and return it
        if (size == 0) return nodes.remove(0);

        Node n = nodes.get(0);
        Node last = nodes.remove(size);
        nodes.set(0, last);
        last.setHeapIndex(0);
        moveDown(last);
        return n;
    }
}
