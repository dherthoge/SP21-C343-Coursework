import java.util.*;
import java.util.function.Consumer;

abstract class GraphTraversal {
    protected final Hashtable<Node, ArrayList<Edge>> neighbors;
    protected NodeCollection nodesToTraverse;

    GraphTraversal(Hashtable<Node, ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
    }

    // Good
    void traverse(Consumer<Node> consumer) {
        while (!nodesToTraverse.isEmpty())
            visit(nodesToTraverse.extract(), consumer);
    }

    // Good
    void visit(Node u, Consumer<Node> consumer) {
        if (u.isNotVisited()) {
            u.setVisited();
            consumer.accept(u);
            neighbors.get(u).forEach(this::relax);
        }
    }

    // Good
    abstract void relax(Edge e);
}

// ----------------------------------------------------------------------

class BFS extends GraphTraversal {

    // Good
    BFS(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        nodesToTraverse = new QUEUE_COLL();
    }

    // Good
    void relax(Edge e) {
        nodesToTraverse.insert(e.getDestination());
    }

    // Good
    List<Node> startFrom(Node s) {
        List<Node> result = new ArrayList<>();
        nodesToTraverse.insert(s);
        traverse(result::add);
        return result;
    }
}

// ----------------------------------------------------------------------

class DFS extends GraphTraversal {

    // Good
    DFS(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        nodesToTraverse = new STACK_COLL();
    }

    // Good
    void relax(Edge e) {
        nodesToTraverse.insert(e.getDestination());
    }

    // Good
    List<Node> startFrom(Node s) {
        List<Node> result = new ArrayList<>();
        nodesToTraverse.insert(s);
        traverse(result::add);
        return result;
    }
}

// ----------------------------------------------------------------------

class TopologicalSort extends GraphTraversal {
    TopologicalSort(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        Set<Node> nodes = neighbors.keySet();
        for (Node n : nodes) n.setValue(0);
        nodesToTraverse = new HEAP_COLL(nodes);
        for (Node n : nodes)
            for (Edge edge : neighbors.get(n))
                edge.getDestination().updateValue(i -> i + 1);
    }

    void relax(Edge e) {
        e.getDestination().updateValue(i -> i - 1);
    }

    Queue<Node> sort() {
        Queue<Node> result = new LinkedList<>();
        traverse(node -> {
            if (node.getValue() != 0) throw new Error("Cycle detected");
            result.offer(node);
        });
        return result;
    }
}

// ----------------------------------------------------------------------

class ShortestPaths extends GraphTraversal  {
    ShortestPaths(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        Set<Node> nodes = neighbors.keySet();
        for (Node n : nodes) n.setValue(Integer.MAX_VALUE);
        nodesToTraverse = new HEAP_COLL(nodes);
    }

    // Dijkstra's edge weight added to the destination's "weight" (in this case "value"
    void relax(Edge e) {
        Node source = e.getSource();
        Node destination = e.getDestination();

        // In the case that there are nodes that are unreachable from the source node, we need to
        // check that the value of the source not is not MAX_VALUE
        if (destination.getValue() > source.getValue()+e.getWeight() && source.getValue() != Integer.MAX_VALUE) {
            destination.setValue(source.getValue() + e.getWeight());
        }
    }

    void fromSource(Node source) {
        source.updateValue(i -> 0);
        traverse(node -> {});
    }
}

