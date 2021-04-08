import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphTraversalTest {

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
}
