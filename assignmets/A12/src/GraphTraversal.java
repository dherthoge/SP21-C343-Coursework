import java.util.*;
import java.util.function.Consumer;

abstract class GraphTraversal {
    protected final Hashtable<Node, ArrayList<Edge>> neighbors;
    protected NodeCollection nodesToTraverse;

    GraphTraversal(Hashtable<Node, ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
    }

    void traverse(Consumer<Node> consumer) {
        while (!nodesToTraverse.isEmpty())
            visit(nodesToTraverse.extract(), consumer);
    }

    void visit(Node u, Consumer<Node> consumer) {
        if (u.isNotVisited()) {
            u.setVisited();
            consumer.accept(u);
            neighbors.get(u).forEach(this::relax);
        }
    }

    abstract void relax(Edge e);
}

// ----------------------------------------------------------------------

class BFS extends GraphTraversal {
    BFS(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        nodesToTraverse = new QUEUE_COLL();
    }

    void relax(Edge e) {
        nodesToTraverse.insert(e.getDestination());
    }

    List<Node> startFrom(Node s) {
        List<Node> result = new ArrayList<>();
        nodesToTraverse.insert(s);
        traverse(result::add);
        return result;
    }
}

// ----------------------------------------------------------------------

class DFS extends GraphTraversal {
    DFS(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        nodesToTraverse = new STACK_COLL();
    }

    void relax(Edge e) {
        nodesToTraverse.insert(e.getDestination());
    }

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
        for (Node n : nodes)
            for (Edge edge : neighbors.get(n))
                edge.getDestination().updateValue(i -> i + 1);
        nodesToTraverse = new HEAP_COLL(nodes);
    }

    void relax(Edge e) {
        e.getDestination().updateAndMoveUp(i -> i - 1);
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

class ShortestPaths extends GraphTraversal {
    ShortestPaths(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        Set<Node> nodes = neighbors.keySet();
        for (Node n : nodes) n.setValue(Integer.MAX_VALUE);
        nodesToTraverse = new HEAP_COLL(nodes);
    }

    void relax(Edge e) {
        int sourceDistance = e.getSource().getValue();
        if (sourceDistance != Integer.MAX_VALUE) {
            int newDistance = sourceDistance + e.getWeight();
            Node neighbor = e.getDestination();
            if (newDistance < neighbor.getValue()) {
                neighbor.updateAndMoveUp(i -> newDistance);
            }
        }
    }

    void fromSource(Node source) {
        source.updateAndMoveUp(i -> 0);
        traverse(node -> {
        });
    }
}

// ----------------------------------------------------------------------
// Single source to single destination variant

class DoneE extends RuntimeException {}

class ShortestPath extends GraphTraversal {
    ShortestPath(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        Set<Node> nodes = neighbors.keySet();
        for (Node n : nodes) n.setValue(Integer.MAX_VALUE);
        nodesToTraverse = new HEAP_COLL(nodes);
    }

    /**
     * This is similar to relax for the general shortest paths
     * algorithm with one change: if the neighbor's value
     * is updated, its previousEdge should be updated at the
     * same time.
     */
    void relax(Edge e) {
        // TODO
        throw new Error("TODO");
    }

    ArrayList<Edge> fromSourceToDestination(Node source, Node destination) {
        try {
            source.updateAndMoveUp(i -> 0);
            traverse(node -> {
                if (node.equals(destination)) throw new DoneE();
            });
        }
        catch (DoneE e) {
            return destination.followPreviousEdge();
        }
        throw new Error("Destination unreachable");
    }
}

// ----------------------------------------------------------------------

class MinimumSpanningTree extends GraphTraversal {
    MinimumSpanningTree(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        Set<Node> nodes = neighbors.keySet();
        for (Node n : nodes) n.setValue(Integer.MAX_VALUE);
        nodesToTraverse = new HEAP_COLL(nodes);
    }

    /**
     * This is again similar to the general shortest paths
     * method except for the following:
     *   - if the neighbor has already been visited, return
     *     immediately
     *   - the value we are trying to minimize is the weight
     *     of the edge only, and
     *   - we also maintain information about previousEdges
     */
    void relax(Edge e) {
        // TODO
        throw new Error("TODO");
    }

    Set<Edge> fromSource(Node source) {
        source.updateAndMoveUp(i -> 0);
        traverse(node -> {
        });
        Set<Edge> result = new HashSet<>();
        for (Node n : neighbors.keySet()) {
            if (n.getPreviousEdge() != null) {
                result.add(n.getPreviousEdge());
            }
        }
        return result;
    }
}
