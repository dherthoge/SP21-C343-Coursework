import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class GraphTraversalTest {
    private Node start;
    private Hashtable<Node, ArrayList<Edge>> neighbors;

    @Test
    void scc() {

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
        neighbors.put(d, new ArrayList<>(Collections.singletonList(new Edge(d,e))));
        neighbors.put(e, new ArrayList<>(Collections.singletonList(new Edge(e,b))));

        StronglyConnected scc = new StronglyConnected(neighbors);
        var ans = scc.findSCCs(start);
        System.out.println("Test scc\n--------");
        for (List<Node> lev : ans) {
            System.out.println(lev);
        }
        System.out.println();
    }

    @Test
    // https://www.geeksforgeeks.org/strongly-connected-components/
    void myTest1() {
        Node a, b, c, d, e;
        a = new Node("a");
        b = new Node("b");
        c = new Node("c");
        d = new Node("d");
        e = new Node("e");

        start = a;
        neighbors = new Hashtable<>();
        neighbors.put(a, new ArrayList<>(Arrays.asList(new Edge(a,c), new Edge(a,d))));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(new Edge(b,a))));
        neighbors.put(c, new ArrayList<>(Collections.singletonList(new Edge(c,b))));
        neighbors.put(d, new ArrayList<>(Collections.singletonList(new Edge(d,e))));
        neighbors.put(e, new ArrayList<>(Collections.emptyList()));

        StronglyConnected scc = new StronglyConnected(neighbors);
        var ans = scc.findSCCs(start);
        System.out.println("Test 1\n------");
        for (List<Node> lev : ans) {
            System.out.println(lev);
        }
        System.out.println();
    }

    @Test
    // the one at the bottom of the page of Lab13
    void myTest2() {
        Node a, b, c, d, e, f, g, h;
        a = new Node("a");
        b = new Node("b");
        c = new Node("c");
        d = new Node("d");
        e = new Node("e");
        f = new Node("f");
        g = new Node("g");
        h = new Node("h");

        start = a;
        neighbors = new Hashtable<>();
        neighbors.put(a, new ArrayList<>(Arrays.asList(new Edge(a,b))));
        neighbors.put(b, new ArrayList<>(Arrays.asList(new Edge(b,d))));
        neighbors.put(c, new ArrayList<>(Arrays.asList(new Edge(c,b), new Edge(c,d), new Edge(c,g))));
        neighbors.put(d, new ArrayList<>(Arrays.asList(new Edge(d,a))));
        neighbors.put(e, new ArrayList<>(Arrays.asList(new Edge(e,d), new Edge(e,f))));
        neighbors.put(f, new ArrayList<>(Arrays.asList(new Edge(f,e))));
        neighbors.put(g, new ArrayList<>(Arrays.asList(new Edge(g,c), new Edge(g,e))));
        neighbors.put(h, new ArrayList<>(Arrays.asList(new Edge(h,f), new Edge(h,g), new Edge(h,
                h))));

        StronglyConnected scc = new StronglyConnected(neighbors);
        var ans = scc.findSCCs(start);
        System.out.println("Test 2\n------");
        for (List<Node> lev : ans) {
            System.out.println(lev);
        }
        System.out.println();
    }

    @Test
    // https://i.stack.imgur.com/8V0Il.png
    void myTest3() {
        Node a, b, c, d, e, f, g, h, i, j;
        a = new Node("a");
        b = new Node("b");
        c = new Node("c");
        d = new Node("d");
        e = new Node("e");
        f = new Node("f");
        g = new Node("g");
        h = new Node("h");
        i = new Node("i");
        j = new Node("j");

        start = a;
        neighbors = new Hashtable<>();
        neighbors.put(a, new ArrayList<>(Arrays.asList(new Edge(a,c), new Edge(a,h))));
        neighbors.put(b, new ArrayList<>(Arrays.asList(new Edge(b,a))));
        neighbors.put(c, new ArrayList<>(Arrays.asList(new Edge(c,d))));
        neighbors.put(d, new ArrayList<>(Arrays.asList(new Edge(d,f))));
        neighbors.put(e, new ArrayList<>(Arrays.asList(new Edge(e,a), new Edge(e,i))));
        neighbors.put(f, new ArrayList<>(Arrays.asList(new Edge(f,j))));
        neighbors.put(g, new ArrayList<>(Arrays.asList(new Edge(g,i))));
        neighbors.put(h, new ArrayList<>(Arrays.asList(new Edge(h,f), new Edge(h,g))));
        neighbors.put(i, new ArrayList<>(Arrays.asList(new Edge(i,h))));
        neighbors.put(j, new ArrayList<>(Arrays.asList(new Edge(j,c))));

        StronglyConnected scc = new StronglyConnected(neighbors);
        var ans = scc.findSCCs(start);
        System.out.println("Test 3\n------");
        for (List<Node> lev : ans) {
            System.out.println(lev);
        }
        System.out.println();
    }
}