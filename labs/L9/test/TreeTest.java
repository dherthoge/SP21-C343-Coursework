import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    // TODO does it work ?
    @Test
    public void sort () {

        Tree tree1 = new Empty();
        tree1 = tree1.insert(5);
        tree1 = tree1.insert(9);
        tree1 = tree1.insert(6);
        tree1 = tree1.insert(11);
        tree1 = tree1.insert(5);
        tree1 = tree1.insert(4);
        tree1 = tree1.insert(2);
        tree1 = tree1.insert(7);
        tree1 = tree1.insert(2);

        ArrayList<Integer> treeInOrder = (ArrayList<Integer>) Sort.inOrder(tree1);
        TreePrinter.print(tree1);
        for (Integer i: treeInOrder) System.out.print(i + ", ");


        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(54);
        numbers.add(-12);
        numbers.add(7);
        numbers.add(100000);
        numbers.add(5);
        numbers.add(-68);
        numbers.add(-70);
        numbers.add(42);
        numbers.add(0);
        numbers.add(11);
        numbers.add(-12451);
        numbers.add(1);

        ArrayList<Integer> tree2InOrder = (ArrayList<Integer>) Sort.sort(numbers);
        System.out.print("\n\n");
        for (Integer i: tree2InOrder) System.out.print(i + ", ");
    }
}
