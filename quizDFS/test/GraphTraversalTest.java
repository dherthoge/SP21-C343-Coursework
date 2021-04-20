import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class GraphTraversalTest {
    private Hashtable<Node, ArrayList<Edge>> neighbors1, neighbors2;
    private List<Node> nodes1, nodes2;

    @BeforeEach
    void graph1() {
        Node a, b, c, d, e;
        a = new Node("a");
        b = new Node("b");
        c = new Node("c");
        d = new Node("d");
        e = new Node("e");

        nodes1 = Arrays.asList(a,b,c,d,e);

        neighbors1 = new Hashtable<>();
        neighbors1.put(a, new ArrayList<>(Arrays.asList(new Edge(a, c), new Edge(a, b))));
        neighbors1.put(b, new ArrayList<>(Arrays.asList(new Edge(b, c), new Edge(b, e), new Edge(b, d))));
        neighbors1.put(c, new ArrayList<>(Collections.emptyList()));
        neighbors1.put(d, new ArrayList<>(Collections.singletonList(new Edge(d, e))));
        neighbors1.put(e, new ArrayList<>(Collections.singletonList(new Edge(e, b))));
    }

    @BeforeEach
    void graph2() {
        Node a, b, c, d, e, f, g, h;
        a = new Node("a");
        b = new Node("b");
        c = new Node("c");
        d = new Node("d");
        e = new Node("e");
        f = new Node("f");
        g = new Node("g");
        h = new Node("h");

        nodes2 = Arrays.asList(a,b,c,d,e,f,g,h);

        neighbors2 = new Hashtable<>();
        neighbors2.put(a, new ArrayList<>(Arrays.asList(new Edge(a, b), new Edge(a, d))));
        neighbors2.put(b, new ArrayList<>(Collections.singletonList(new Edge(b, c))));
        neighbors2.put(c, new ArrayList<>(Collections.singletonList(new Edge(c, h))));
        neighbors2.put(d, new ArrayList<>(Arrays.asList(new Edge(d, e), new Edge(d,f))));
        neighbors2.put(e, new ArrayList<>(Collections.singletonList(new Edge(e, h))));
        neighbors2.put(f, new ArrayList<>(Collections.singletonList(new Edge(f, g))));
        neighbors2.put(g, new ArrayList<>(Collections.singletonList(new Edge(g, h))));
        neighbors2.put(h, new ArrayList<>());
    }

    @Test
    void dfs1() {
        System.out.println("DFS1");
        DFS dfs = new DFS(neighbors1, nodes1.get(0));
        System.out.println(dfs.nodes);
        // [a, c, b, e, d]
    }

    @Test
    void reach1() {
        System.out.println("REACH1");
        Reachability reach = new Reachability(neighbors1, nodes1.get(0));
        System.out.println(reach.table);
        // {b=[a, b, e], a=[], e=[a, b, d], d=[a, b], c=[a, b]}
    }

    @Test
    void topologicalsort1() {
        System.out.println("TOPOLOGICALSORT1");
        TopologicalSort topologicalSort = new TopologicalSort(neighbors2, nodes2.get(0));
        System.out.println(topologicalSort.nodes);
        // [a, d, f, g, e, b, c, h]
    }

    @Test
    void cycledetection1() {
        System.out.println("CYCLEDETECTION1");
        CycleDetection cycleDetection = new CycleDetection(neighbors1, nodes1.get(0));
        System.out.println(cycleDetection.hasCycle);
        // true
    }

    @Test
    void cycledetection2() {
        System.out.println("CYCLEDETECTION2");
        CycleDetection cycleDetection = new CycleDetection(neighbors2, nodes2.get(0));
        System.out.println(cycleDetection.hasCycle);
        // false
    }

}