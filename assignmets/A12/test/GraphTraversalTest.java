import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphTraversalTest {
    private Node start;
    private Hashtable<Node, ArrayList<Edge>> neighbors;

    void simpleGraph() {
        Node a, b, c, d, e;
        a = new Node("a");
        b = new Node("b");
        c = new Node("c");
        d = new Node("d");
        e = new Node("e");

        start = a;
        neighbors = new Hashtable<>();
        neighbors.put(a, new ArrayList<>(Arrays.asList(new Edge(a,c), new Edge(a,b))));
        neighbors.put(b, new ArrayList<>(Arrays.asList(new Edge(b,c), new Edge(b,e), new Edge(b,d))));
        neighbors.put(c, new ArrayList<>(Collections.emptyList()));
        neighbors.put(d, new ArrayList<>(Collections.singletonList(new Edge(d, e))));
        neighbors.put(e, new ArrayList<>(Collections.emptyList()));
    }

    @Test public void bfs () {
        simpleGraph();
        BFS g = new BFS(neighbors);
        List<Node> bfsTraversal = g.startFrom(start);
        System.out.println(bfsTraversal);
    }

    @Test
    public void dfs () {
        simpleGraph();
        DFS g = new DFS(neighbors);
        List<Node> dfsTraversal = g.startFrom(start);
        System.out.println(dfsTraversal);
    }

    @Test
    public void topological1 () {
        simpleGraph();
        TopologicalSort g = new TopologicalSort(neighbors);
        System.out.println(g.sort());
    }

    @Test
    public void topological2 () {
        Node v1 = new Node("v1");
        Node v2 = new Node("v2");
        Node v3 = new Node("v3");
        Node v4 = new Node("v4");
        Node v5 = new Node("v5");
        Node v6 = new Node("v6");
        Node v7 = new Node("v7");

        Edge v12 = new Edge(v1,v2);
        Edge v13 = new Edge(v1,v3);
        Edge v14 = new Edge(v1,v4);
        Edge v24 = new Edge(v2,v4);
        Edge v25 = new Edge(v2,v5);
        Edge v36 = new Edge(v3,v6);
        Edge v43 = new Edge(v4,v3);
        Edge v46 = new Edge(v4,v6);
        Edge v47 = new Edge(v4,v7);
        Edge v54 = new Edge(v5,v4);
        Edge v57 = new Edge(v5,v7);
        Edge v76 = new Edge(v7,v6);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(v1, new ArrayList<>(Arrays.asList(v12,v13,v14)));
        neighbors.put(v2, new ArrayList<>(Arrays.asList(v24,v25)));
        neighbors.put(v3, new ArrayList<>(Collections.singletonList(v36)));
        neighbors.put(v4, new ArrayList<>(Arrays.asList(v43,v46,v47)));
        neighbors.put(v5, new ArrayList<>(Arrays.asList(v54,v57)));
        neighbors.put(v6, new ArrayList<>());
        neighbors.put(v7, new ArrayList<>(Collections.singletonList(v76)));

        TopologicalSort g = new TopologicalSort(neighbors);
        Queue<Node> sort = g.sort();
        assertEquals(7, sort.size());
        assertEquals(v1,sort.poll());
        assertEquals(v2,sort.poll());
        assertEquals(v5,sort.poll());
        assertEquals(v4,sort.poll());
        assertEquals(v3,sort.poll());
        assertEquals(v7,sort.poll());
        assertEquals(v6,sort.poll());
    }

    @Test
    void shortestPath1 () {
        Node s = new Node("s");
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node f = new Node("f");
        Node t = new Node("t");

        Edge sa = new Edge(s,a,3);
        Edge sb = new Edge(s,b,4);
        Edge ab = new Edge(a,b,6);
        Edge bf = new Edge(b,f,5);
        Edge af = new Edge(a,f,7);
        Edge ac = new Edge(a,c,2);
        Edge cf = new Edge(c,f,1);
        Edge ct = new Edge(c,t,8);
        Edge ft = new Edge(f,t,4);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa,sb)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab,af,ac)));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(bf)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cf,ct)));
        neighbors.put(f, new ArrayList<>(Collections.singletonList(ft)));
        neighbors.put(t, new ArrayList<>());

        ShortestPaths g = new ShortestPaths(neighbors);
        g.fromSource(s);
        assertEquals(0, s.getValue());
        assertEquals(3, a.getValue());
        assertEquals(4, b.getValue());
        assertEquals(5, c.getValue());
        assertEquals(6, f.getValue());
        assertEquals(10, t.getValue());
    }

    @Test
    public void shortestPath2 () {
        Node a0 = new Node("0");
        Node a1 = new Node("1");
        Node a2 = new Node("2");
        Node a3 = new Node("3");
        Node a4 = new Node("4");
        Node a5 = new Node("5");
        Node a6 = new Node("6");
        Node a7 = new Node("7");

        Edge e04 = new Edge(a0, a4, 6);
        Edge e13 = new Edge(a1, a3, 5);
        Edge e16 = new Edge(a1, a6, 2);
        Edge e24 = new Edge(a2, a4, 7);
        Edge e25 = new Edge(a2, a5, 7);
        Edge e35 = new Edge(a3, a5, 2);
        Edge e37 = new Edge(a3, a7, 1);
        Edge e46 = new Edge(a4, a6, 3);
        Edge e57 = new Edge(a5, a7, 7);
        Edge e62 = new Edge(a6, a2, 7);
        Edge e67 = new Edge(a6, a7, 6);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(a0, new ArrayList<>(Collections.singletonList(e04)));
        neighbors.put(a1, new ArrayList<>(Arrays.asList(e13, e16)));
        neighbors.put(a2, new ArrayList<>(Arrays.asList(e24, e25)));
        neighbors.put(a3, new ArrayList<>(Arrays.asList(e35, e37)));
        neighbors.put(a4, new ArrayList<>(Collections.singletonList(e46)));
        neighbors.put(a5, new ArrayList<>(Collections.singletonList(e57)));
        neighbors.put(a6, new ArrayList<>(Arrays.asList(e62, e67)));
        neighbors.put(a7, new ArrayList<>(Collections.emptyList()));

        ShortestPaths g = new ShortestPaths(neighbors);
        g.fromSource(a0);
        assertEquals(0, a0.getValue());
        assertEquals(Integer.MAX_VALUE, a1.getValue());
        assertEquals(16, a2.getValue());
        assertEquals(Integer.MAX_VALUE, a3.getValue());
        assertEquals(6, a4.getValue());
        assertEquals(23, a5.getValue());
        assertEquals(9, a6.getValue());
        assertEquals(15, a7.getValue());
    }

    @Test
    public void spanningTree1() {
        Node s = new Node("s");
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node t = new Node("t");

        Edge sa = new Edge(s, a, 7);
        Edge sc = new Edge(s, c, 8);
        Edge ab = new Edge(a, b, 6);
        Edge ac = new Edge(a, c, 3);
        Edge cb = new Edge(c, b, 4);
        Edge cd = new Edge(c, d, 3);
        Edge bd = new Edge(b, d, 2);
        Edge bt = new Edge(b, t, 5);
        Edge dt = new Edge(d, t, 2);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa, sc)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ac, ab, sa.flip())));
        neighbors.put(b, new ArrayList<>(Arrays.asList(bd,bt,ab.flip(),cb.flip())));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cb, cd, ac.flip(), sc.flip())));
        neighbors.put(d, new ArrayList<>(Arrays.asList(dt,bd.flip(),cd.flip())));
        neighbors.put(t, new ArrayList<>(Arrays.asList(bt.flip(),dt.flip())));

	MinimumSpanningTree graph = new MinimumSpanningTree(neighbors);

        Set<Edge> tree = graph.fromSource(s);
        assertEquals(5, tree.size());
        System.out.println(tree);
        assertTrue(tree.contains(sa));
        assertTrue(tree.contains(ac));
        assertTrue(tree.contains(cd));
        assertTrue(tree.contains(bd.flip()));
        assertTrue(tree.contains(dt));
    }

    @Test
    public void spanningTree2() {
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");
        Node g = new Node("g");

        Edge ab = new Edge(a, b, 7);
        Edge ad = new Edge(a, d, 5);
        Edge ba = new Edge(b, a, 7);
        Edge bc = new Edge(b, c, 8);
        Edge bd = new Edge(b, d, 9);
        Edge be = new Edge(b, e, 7);
        Edge cb = new Edge(c, b, 8);
        Edge ce = new Edge(c, e, 5);
        Edge da = new Edge(d, a, 5);
        Edge db = new Edge(d, b, 9);
        Edge de = new Edge(d, e, 15);
        Edge df = new Edge(d, f, 6);
        Edge eb = new Edge(e, b, 7);
        Edge ec = new Edge(e, c, 5);
        Edge ed = new Edge(e, d, 15);
        Edge ef = new Edge(e, f, 8);
        Edge eg = new Edge(e, g, 9);
        Edge fd = new Edge(f, d, 6);
        Edge fe = new Edge(f, e, 8);
        Edge fg = new Edge(f, g, 1);
        Edge ge = new Edge(g, e, 9);
        Edge gf = new Edge(g, f, 1);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab, ad)));
        neighbors.put(b, new ArrayList<>(Arrays.asList(ba, bc, bd, be)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cb, ce)));
        neighbors.put(d, new ArrayList<>(Arrays.asList(da, db, de, df)));
        neighbors.put(e, new ArrayList<>(Arrays.asList(eb, ec, ed, ef, eg)));
        neighbors.put(f, new ArrayList<>(Arrays.asList(fd, fe, fg)));
        neighbors.put(g, new ArrayList<>(Arrays.asList(ge, gf)));

        MinimumSpanningTree graph = new MinimumSpanningTree(neighbors);

        Set<Edge> tree = graph.fromSource(a);
        assertEquals(6, tree.size());
        System.out.println(tree);

    }

    @Test
    void shortestPathDest1 () {
        Node s = new Node("s");
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node f = new Node("f");
        Node t = new Node("t");

        Edge sa = new Edge(s,a,3);
        Edge sb = new Edge(s,b,4);
        Edge ab = new Edge(a,b,6);
        Edge bf = new Edge(b,f,5);
        Edge af = new Edge(a,f,7);
        Edge ac = new Edge(a,c,2);
        Edge cf = new Edge(c,f,1);
        Edge ct = new Edge(c,t,8);
        Edge ft = new Edge(f,t,4);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa,sb)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab,af,ac)));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(bf)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cf,ct)));
        neighbors.put(f, new ArrayList<>(Collections.singletonList(ft)));
        neighbors.put(t, new ArrayList<>());

        ShortestPath graph = new ShortestPath(neighbors);

        ArrayList<Edge> path = graph.fromSourceToDestination(s,t);
        assertEquals(4,path.size());

        assertEquals(sa,path.get(0));
        assertEquals(ac,path.get(1));
        assertEquals(cf,path.get(2));
        assertEquals(ft,path.get(3));
    }

    @Test
    public void shortestPathDest2 () {
        Node a0 = new Node("0");
        Node a1 = new Node("1");
        Node a2 = new Node("2");
        Node a3 = new Node("3");
        Node a4 = new Node("4");
        Node a5 = new Node("5");
        Node a6 = new Node("6");
        Node a7 = new Node("7");

        Edge e04 = new Edge(a0, a4, 6);
        Edge e13 = new Edge(a1, a3, 5);
        Edge e16 = new Edge(a1, a6, 2);
        Edge e24 = new Edge(a2, a4, 7);
        Edge e25 = new Edge(a2, a5, 7);
        Edge e35 = new Edge(a3, a5, 2);
        Edge e37 = new Edge(a3, a7, 1);
        Edge e46 = new Edge(a4, a6, 3);
        Edge e57 = new Edge(a5, a7, 7);
        Edge e62 = new Edge(a6, a2, 7);
        Edge e67 = new Edge(a6, a7, 6);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(a0, new ArrayList<>(Collections.singletonList(e04)));
        neighbors.put(a1, new ArrayList<>(Arrays.asList(e13, e16)));
        neighbors.put(a2, new ArrayList<>(Arrays.asList(e24, e25)));
        neighbors.put(a3, new ArrayList<>(Arrays.asList(e35, e37)));
        neighbors.put(a4, new ArrayList<>(Collections.singletonList(e46)));
        neighbors.put(a5, new ArrayList<>(Collections.singletonList(e57)));
        neighbors.put(a6, new ArrayList<>(Arrays.asList(e62, e67)));
        neighbors.put(a7, new ArrayList<>(Collections.emptyList()));

        ShortestPath graph = new ShortestPath(neighbors);
        ArrayList<Edge> path = graph.fromSourceToDestination(a0, a7);
        System.out.println(path);
    }
}
