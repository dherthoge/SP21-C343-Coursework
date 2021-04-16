import java.util.*;
import java.util.function.Consumer;

abstract class GraphTraversal {
    protected Hashtable<Node, ArrayList<Edge>> neighbors;
    protected NodeCollection nodesToTraverse;

    GraphTraversal(Hashtable<Node, ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
    }

    void traverse(Consumer<Node> consumer) {
        while (!nodesToTraverse.isEmpty())
            visit(nodesToTraverse.extract(), consumer);
    }

    void visit(Node u, Consumer<Node> consumer) {
        if (!u.isVisited()) {
            u.setVisited();
            consumer.accept(u);
            neighbors.get(u).forEach(this::relax);
        }
    }

    abstract void relax(Edge e);
}

class StronglyConnected extends GraphTraversal {

    StronglyConnected(Hashtable<Node, ArrayList<Edge>> neighbors) {
        super(neighbors);
        nodesToTraverse = new STACK_COLL();
    }

    // This method should reverse every edge in our graph.
    // If we had the following graph:
    //   a --(2)--> b
    // Taking the transpose would give us:
    //   a <--(2)-- b
    Hashtable<Node, ArrayList<Edge>> transpose() {

        // Copy the Nodes stored in neighbors for all of the Nodes to start the reversed graph
        Hashtable<Node, ArrayList<Edge>> reversedNeighbors = new Hashtable<>();
        for (Node n : neighbors.keySet()) {
            reversedNeighbors.put(n, new ArrayList<>());
        }

        // For each edge, reverse it and add it to the reversed graph
        for (ArrayList<Edge> es : neighbors.values()) {
            for (Edge e: es) {
                Edge reversedEdge = e.flip();
                reversedNeighbors.get(reversedEdge.getSource()).add(reversedEdge);
            }
        }

        // Return the reversed graph
        return reversedNeighbors;
    }


    void relax(Edge e) {
        nodesToTraverse.insert(e.getDestination());
    }

    // Depth-first search with a stack. Our stack is nodesToTraverse.
    List<Node> DFS(Node n) {
        List<Node> result = new ArrayList<>();
        nodesToTraverse.insert(n);
        traverse(result::add);
        return result;
    }

    // Kosaraju's Algorithm
    List<List<Node>> findSCCs(Node s) {
        LinkedList<List<Node>> stackofcomponents = new LinkedList<>();
        List<List<Node>> ans = new ArrayList<>();

        // Our first DFS
        for (Node n : neighbors.keySet()) {
            if (!n.isVisited()) {
                var dfs = DFS(n);
                stackofcomponents.addFirst(dfs);
            }
        }

        this.neighbors = transpose();
        neighbors.keySet().forEach(Node::reset);

        // We have successfully finished our first DFS, along with getting the transpose of the graph...
        // There might be more SCCs inside our stack of components, we should DFS again.
        // What do we need to do here with our stackofcomponets? Pay attention to your types in the lines below,
        // they give a huge hint. Replace "???".

        for (List<Node> l : stackofcomponents) {
            for (Node n : l) {
                if (!n.isVisited()) ans.add(DFS(n));
            }
        }

        return ans;
    }
}
