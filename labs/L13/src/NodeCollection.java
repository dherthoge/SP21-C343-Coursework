import java.util.*;

abstract class NodeCollection {
    abstract boolean isEmpty();
    abstract void insert(Node n);
    abstract Node extract();
}

class STACK_COLL extends NodeCollection {
    private final Stack<Node> stack;
    STACK_COLL() {
        this.stack = new Stack<>();
    }

    boolean isEmpty() { return stack.isEmpty(); }
    void insert(Node elem) { stack.push(elem); }
    Node extract() { return stack.pop(); }
}