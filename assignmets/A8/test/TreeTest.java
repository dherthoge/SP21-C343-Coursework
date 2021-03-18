import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    public void basic () {
        Tree t1, t2, t3, t4, t5, t6;
        t1 = new Node(5, new Empty(), new Empty());
        t2 = new Node(30, new Empty(), new Empty());
        t3 = new Node(2, t1, t2);
        t4 = new Node(38, t3.map(k -> k+1), t3.mirror().insert(100));
        t5 = Tree.fromArray(new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        t6 = new Node(0, new Node(-1, new Empty(), new Empty()), t5);

        TreePrinter.print(t1);
        TreePrinter.print(t2);
        TreePrinter.print(t3);
        TreePrinter.print(t4);
        TreePrinter.print(t5);
        TreePrinter.print(t6);

        assertEquals(4, t4.height());
        assertEquals(2, t4.numberMaxPaths());
        assertEquals(16, t5.numberMaxPaths());
        assertEquals(6, t4.diameter());
        assertEquals(7, t5.diameter());
        assertEquals(7, t6.diameter());
        assertEquals(215, t4.reduce(0, (a,b,c) -> a+b+c));
        assertEquals(170, t4.maxSum());
    }

    // My Tests

    @Test
    public void insert () {
        Tree t1, t2, t3, t4, t5, t6, t7;
        t1 = new Node(1, new Empty(), new Empty());
        TreePrinter.print(t1);
        t2 = t1.insert(2);
        TreePrinter.print(t2);
        t3 = t2.insert(3);
        TreePrinter.print(t3);
        t4 = t3.insert(4);
        TreePrinter.print(t4);
        t5 = t4.insert(5);
        TreePrinter.print(t5);
        t6 = t5.insert(6);
        TreePrinter.print(t6);
        t7 = t6.insert(7);
        TreePrinter.print(t7);
    }

    @Test
    public void BFS () {
        Tree t1, t2, t3, t4, t5, t6, t7;
        t1 = new Node(1, new Empty(), new Empty());
        t2 = t1.insert(2);
        t3 = t2.insert(3);
        t4 = t3.insert(4);
        t5 = t4.insert(5);
        t6 = t5.insert(6);
        t7 = t6.insert(7);
        TreePrinter.print(t7);

        ArrayList<Integer> tree = Tree.BFS(t7);
        for (Integer i : tree) System.out.print(i);
    }

    @Test
    public void BFSLevel () {
        Tree t1, t2, t3, t4, t5, t6, t7;
        t1 = new Node(1, new Empty(), new Empty());
        t2 = t1.insert(2);
        t3 = t2.insert(3);
        t4 = t3.insert(4);
        t5 = t4.insert(5);
        t6 = t5.insert(6);
        t7 = t6.insert(7);
        TreePrinter.print(t7);

        ArrayList<ArrayList<Integer>> BFSLeveltree = Tree.BFSLevel(t7);

        System.out.print("[");
        for (ArrayList<Integer> arr : BFSLeveltree) {
            System.out.print("[");
            for (Integer i : arr) {
                System.out.print(i + ",");
            }
            System.out.print("]");
        }
        System.out.print("]");
    }

}