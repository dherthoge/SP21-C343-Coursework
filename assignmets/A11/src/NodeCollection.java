import java.util.*;

// Good
abstract class NodeCollection {
    abstract boolean isEmpty();
    abstract void insert(Node n);
    abstract Node extract();
}

// Good
class QUEUE_COLL extends NodeCollection {
    private final Queue<Node> queue;
    QUEUE_COLL() {
        this.queue = new LinkedList<>();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
    void insert(Node elem) {
        queue.offer(elem);
    }
    Node extract() {
        return queue.poll();
    }
}

// Good
class STACK_COLL extends NodeCollection {
    private final Stack<Node> stack;
    STACK_COLL() {
        this.stack = new Stack<>();
    }

    boolean isEmpty() { return stack.isEmpty(); }
    void insert(Node elem) { stack.push(elem); }
    Node extract() { return stack.pop(); }
}

// Good
class HEAP_COLL extends NodeCollection {
    private final Heap heap;
    HEAP_COLL(Set<Node> nodes) {
        this.heap = new Heap(nodes);
    }

    boolean isEmpty() {
        return heap.isEmpty();
    }
    void insert(Node elem) {
        heap.insert(elem);
    }
    Node extract() {
        return heap.extractMin();
    }
}

