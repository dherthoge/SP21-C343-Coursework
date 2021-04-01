import com.sun.source.tree.Tree;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AVLTest {

    @Test
    public void AVLinsert() {
        AVL.resetRotations();
        List<Integer> nums = IntStream.range(0, 4096).boxed().collect(Collectors.toList());
        Collections.shuffle(nums, new Random(100));
        AVL<Integer> avl = new Empty<>();
        for (int i : nums) avl = avl.insert(i);
        assertEquals(14, avl.height());
        assertEquals(1430, AVL.getRRotations());
        assertEquals(1425, AVL.getLRotations());
    }

    @Test
    public void dup () {
        AVL<Integer> avl = new Empty<>();
        avl = avl.insert(1);
        avl = avl.insert(1);
        avl = avl.insert(1);
        avl = avl.insert(1);
        avl = avl.insert(1);
        avl = avl.insert(1);
        avl = avl.insert(1);
        avl = avl.insert(1);
        avl = avl.insert(1);

        avl = avl.delete(1);
        avl = avl.delete(1);
        avl = avl.delete(1);
        avl = avl.delete(1);
        avl = avl.delete(1);
        avl = avl.delete(1);
        avl = avl.delete(1);
        avl = avl.delete(1);
        avl = avl.delete(1);

        assertTrue(avl.isEmpty());
    }

    @Test
    public void AVLeasyRight() throws EmptyAVLE {
        AVL.resetRotations();
        AVL<Integer> avl = new Empty<>();
        avl = avl.insert(40);
        assertEquals(0, AVL.getRotations());

        avl = avl.insert(50);
        assertEquals(0, AVL.getRotations());

        avl = avl.insert(20);
        assertEquals(0, AVL.getRotations());

        avl = avl.insert(10);
        assertEquals(0, AVL.getRotations());

        avl = avl.insert(30);
        assertEquals(0, AVL.getRotations());
        TreePrinter.print(avl);

        avl = avl.insert(15);
        assertEquals(1, AVL.getRRotations());
        assertEquals(0, AVL.getLRotations());
        TreePrinter.print(avl);

        AVL<Integer> left = avl.left();
        AVL<Integer> right = avl.right();
        assertEquals(20, avl.data());
        assertEquals(10,left.data());
        assertEquals(15, left.right().data());
        assertEquals(40,right.data());
        assertEquals(30, right.left().data());
        assertEquals(50, right.right().data());
    }

    @Test
    public void AVLrotateRight() throws EmptyAVLE {
        AVL.resetRotations();
        AVL<Integer> avl = new Empty<>();
        avl = avl.insert(40);
        avl = avl.insert(50);
        avl = avl.insert(20);
        avl = avl.insert(10);
        avl = avl.insert(30);
        assertEquals(0, AVL.getRotations());

        avl = avl.insert(25);
        assertEquals(1, AVL.getRRotations());
        assertEquals(1, AVL.getLRotations());

        AVL<Integer> left = avl.left();
        AVL<Integer> right = avl.right();
        assertEquals(30, avl.data());
        assertEquals(20,left.data());
        assertEquals(10, left.left().data());
        assertEquals(25, left.right().data());
        assertEquals(40,right.data());
        assertEquals(50, right.right().data());
    }

    @Test
    public void AVLdelete() throws EmptyAVLE {
        AVL.resetRotations();
        AVL<Integer> avl = new Empty<>();
        avl = avl.insert(35);
        avl = avl.insert(20);
        avl = avl.insert(40);
        avl = avl.insert(7);
        avl = avl.insert(30);
        avl = avl.insert(50);
        avl = avl.insert(5);
        avl = avl.insert(10);
        assertEquals(0,AVL.getRotations());

        TreePrinter.print(avl);
        AVL<Integer> avl2 = avl.delete(35);
        assertEquals(1,AVL.getRRotations());
        assertEquals(0,AVL.getLRotations());

        AVL<Integer> left = avl2.left();
        AVL<Integer> right = avl2.right();
        assertEquals(20, avl2.data());
        assertEquals(7,left.data());
        assertEquals(5, left.left().data());
        assertEquals(10, left.right().data());
        assertEquals(40,right.data());
        assertEquals(30, right.left().data());
        assertEquals(50, right.right().data());
    }

    @Test
    public void AVLdelete2 () {
        AVL.resetRotations();
        AVL<Integer> t = new Empty<>();
        t = t.insert(44);
        t = t.insert(17);
        t = t.insert(78);
        t = t.insert(32);
        t = t.insert(50);
        t = t.insert(88);
        t = t.insert(48);
        t = t.insert(62);
        assertEquals(0,AVL.getRotations());
        t.delete(32);
        assertEquals(1,AVL.getRRotations());
        assertEquals(1,AVL.getLRotations());
    }


    // My Tests

    @Test
    public void AVLfind() {
        AVL.resetRotations();
        List<Integer> nums = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Collections.shuffle(nums, new Random(100));
        AVL<Integer> avl = new Empty<>();
        for (int i : nums) avl = avl.insert(i);

        assertTrue(avl.find(0));
        assertTrue(avl.find(1));
        assertTrue(avl.find(2));
        assertTrue(avl.find(3));
        assertTrue(avl.find(4));
        assertTrue(avl.find(5));
        assertTrue(avl.find(6));
        assertTrue(avl.find(7));
        assertTrue(avl.find(8));
        assertTrue(avl.find(9));

        assertTrue(!avl.find(11));
        assertTrue(!avl.find(12));
        assertTrue(!avl.find(13));
        assertTrue(!avl.find(14));
        assertTrue(!avl.find(15));
    }

    @Test
    public void AVLextractLeftMost() throws EmptyAVLE {

        AVL<Integer> avl = new Node<>(7, new Node<>(3, new Node<>(6, new Node<>(10, new Empty<>()
                , new Empty<>()), new Node<>(15, new Node<>(18, new Empty<>(), new Empty<>()),
                new Empty<>())), new Node<>(7, new Empty<>(), new Empty<>())),
                new Node<>(70,
                new Empty<>(),
                new Node<>(80, new Empty<>(), new Empty<>())));

        TreePrinter.print(avl);

//        TreePrinter.print(avl);
//        avl = avl.rotateRight();
////        TreePrinter.print(avl);
//        avl = avl.rotateRight();
////        TreePrinter.print(avl);
//        avl = avl.rotateRight();
////        TreePrinter.print(avl);
//
//
//        avl = (Node<Integer>) avl.right();
//        TreePrinter.print(avl);
//
//
//        avl = avl.rotateLeft();
//        TreePrinter.print(avl);
//        avl = avl.rotateLeft();
//        TreePrinter.print(avl);

        Pair<Integer, AVL<Integer>> call1 = avl.extractLeftMost();
        avl = call1.getSecond();
        System.out.println(call1.getFirst());
        TreePrinter.print(call1.getSecond());
        Pair<Integer, AVL<Integer>> call2 = avl.extractLeftMost();
        System.out.println(call2.getFirst());
        TreePrinter.print(call2.getSecond());
//        TreePrinter.print(avl.findLeftMost());

//        Empty<Integer> empty = new Empty<>();
//        TreePrinter.print(new Node<>(2, new Node<>(1, empty, empty), new Node<>(4,
//                new Node<>(3, empty, empty), empty)));

//        assertEquals(new Pair<>(0, ));
    }
}

