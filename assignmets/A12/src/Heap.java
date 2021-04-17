import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

class Heap {
    private final ArrayList<Node> nodes;
    private int size;

    Heap (Set<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
        this.size = nodes.size();
        for (int i=0; i<size; i++) {
            this.nodes.get(i).setHeap(this);
            this.nodes.get(i).setHeapIndex(i);
        }
        heapify();
    }

    void heapify () {
        for (int i=size/2; i>=0; i--) moveDown(nodes.get(i));
    }

    boolean isEmpty () {
        return size == 0;
    }

    Optional<Node> getParent(Node n) {
        int parentIndex = (n.getHeapIndex() - 1) / 2;
        if (parentIndex < 0) return Optional.empty();
        return Optional.of(nodes.get(parentIndex));
    }

    Optional<Node> getLeftChild(Node n) {
        int childIndex = 2 * n.getHeapIndex() + 1;
        if (childIndex >= size) return Optional.empty();
        return Optional.of(nodes.get(childIndex));
    }

    Optional<Node> getRightChild(Node n) {
        int childIndex = 2 * n.getHeapIndex() + 2;
        if (childIndex >= size) return Optional.empty();
        return Optional.of(nodes.get(childIndex));
    }

    Optional<Node> getMinChild(Node n) {
        Optional<Node> leftChild = getLeftChild(n);
        Optional<Node> rightChild = getRightChild(n);
        if (rightChild.isEmpty()) return leftChild;
        else {
            assert leftChild.isPresent();
            return Optional.of(Node.min(leftChild.get(), rightChild.get()));
        }
    }

    void swap(Node n1, Node n2) {
        int p1 = n1.getHeapIndex();
        int p2 = n2.getHeapIndex();
        nodes.set(p1, n2);
        n2.setHeapIndex(p1);
        nodes.set(p2, n1);
        n1.setHeapIndex(p2);
    }

    void moveDown(Node n) {
        Optional<Node> minChild = getMinChild(n);
        minChild.ifPresent(c -> {
            if (c.compareTo(n) < 0) {
                swap(n, c);
                moveDown(n);
            }
        });
    }

    void moveUp(Node n) {
        Optional<Node> parent = getParent(n);
        parent.ifPresent(p -> {
            if (n.compareTo(p) < 0) {
                swap(n, p);
                moveUp(n);
            }
        });
    }

    void insert (Node n) {
        n.setHeapIndex(size);
        nodes.set(size++,n);
        moveUp(n);
    }

    Node extractMin() {
        Node n = nodes.get(0);
        Node last = nodes.get(--size);
        nodes.set(0, last);
        last.setHeapIndex(0);
        moveDown(last);
        return n;
    }
}
