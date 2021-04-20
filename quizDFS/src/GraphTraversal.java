import java.util.*;
import java.util.function.Consumer;

abstract class GenericDFS {
    protected final Hashtable<Node, ArrayList<Edge>> neighbors;
    private Consumer<Node> touchConsumer, enterConsumer, exitConsumer;

    GenericDFS(Hashtable<Node, ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
        this.touchConsumer = node -> {};
        this.enterConsumer = node -> {};
        this.exitConsumer = node -> {};
    }

    void traverse(Node u) {
        if (u.isVisited()) touchConsumer.accept(u);
        else {
            u.setVisited();
            enterConsumer.accept(u);
            neighbors.get(u).forEach(e -> traverse(e.getDestination()));
            exitConsumer.accept(u);
        }
    }

    void setTouchConsumer (Consumer<Node> consumer) {
        this.touchConsumer = consumer;
    }
    void setEnterConsumer (Consumer<Node> consumer) {
        this.enterConsumer = consumer;
    }
    void setExitConsumer (Consumer<Node> consumer) {
        this.exitConsumer = consumer;
    }
}

// ----------------------------------------------------------------------

class DFS extends GenericDFS {
    final List<Node> nodes;

    DFS(Hashtable<Node, ArrayList<Edge>> neighbors, Node start) {
        super(neighbors);

        /*
        For a conventional DFS traversal, we collect the nodes in a
        a list as we visit them. The only consumer we need to
        customize is the enterConsumer which is invoked when we
        first encounter a new node. The action to perform in that
        case is to add the node to the list we are collecting.
         */

        nodes = new ArrayList<>();
        setEnterConsumer(nodes::add);

        /*
        One the search parameters are customized we invoke
        traverse...
         */
        traverse(start);
    }
}

// ----------------------------------------------------------------------

class TopologicalSort extends GenericDFS {
    final List<Node> nodes;

    TopologicalSort(Hashtable<Node, ArrayList<Edge>> neighbors, Node start) {
        super(neighbors);

        /*
        This implementation of topologicalSort assumes the graph has no cycles.
        It relies on the following observations:

          - if we have an edge v -> w, then v must appear before w
            in the topological order

          - in a DFS, if we have an edge v -> w, when we visit v,
            we recursively visit w; the recursive call for w must
            exit before the recursive call for v

          - more generally, in a DFS, when we are about to exit v,
            all the nodes reachable from v have already exited

         */
        nodes = new LinkedList<>();
        //
        // TODO
        //
        this.setTouchConsumer(nodes::add);

        /*
        One the search parameters are customized we invoke
        traverse...
         */
        traverse(start);
    }
}

// ----------------------------------------------------------------------

class CycleDetection extends GenericDFS {
    boolean hasCycle;

    CycleDetection(Hashtable<Node, ArrayList<Edge>> neighbors, Node start) {
        super(neighbors);

        /*
        To detect a cycle during DFS, we main a list of current
        ancestors as follows:

          - when we enter a node v, we add it to the list of
            current ancestors before visiting its neighbors

          - after processing v's neighbors, we remove v from
            the list of current ancestors

          - if, during DFS, we touch a node that is its own ancestor
            then we have detected a cycle
         */
        List<Node> ancestors = new ArrayList<>();
        hasCycle = false;
        //
        // TODO
        //

        this.setTouchConsumer(n -> {
            if (ancestors.contains(n)) {
                hasCycle = true;
            }
        });
        this.setEnterConsumer(n -> ancestors.add(n));
        this.setExitConsumer(n -> ancestors.remove(n));

        /*
        One the search parameters are customized we invoke
        traverse...
         */
        traverse(start);
    }
}

// ----------------------------------------------------------------------

class Reachability extends GenericDFS {
    final Hashtable<Node,Set<Node>> table;

    Reachability(Hashtable<Node, ArrayList<Edge>> neighbors, Node start) {
        super(neighbors);

        /*
        The goal here is to collect, in a hashtable, all the nodes
        that can reach a given node. For example, if a -> b and c -> b
        then both (a,c) would be in the list of nodes that can reach b.

        The main idea needed to compute this table during DFS, is that
        for each edge v -> w, we have the following:
          - v can reach w,
          - all the nodes that can reach v can also reach w
         */
        table = new Hashtable<>();
        for (Node n : neighbors.keySet()) table.put(n, new HashSet<>());
        //
        // TODO
        //

        setEnterConsumer(u -> {

            ArrayList<Edge> edges = neighbors.get(u);
            edges.forEach(e -> {
                table.get(e.getDestination()).add(u);
            });
        });
        setExitConsumer(u -> {

        });

        /*
        One the search parameters are customized we invoke
        traverse...
         */
        traverse(start);
    }
}

// ----------------------------------------------------------------------

