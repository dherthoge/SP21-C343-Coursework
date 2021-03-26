import org.junit.jupiter.api.Test;

public class BSTTest {

    // TODO does it work ?
    @Test
    public void delete () {
        BST bst1 = new BSTNode(10, new Empty(), new Empty());
        bst1 = bst1.insert(7);
        bst1 = bst1.insert(9);
        bst1 = bst1.insert(3);
        bst1 = bst1.insert(2);
        bst1 = bst1.insert(4);
        bst1 = bst1.insert(1);
        bst1 = bst1.insert(6);
        bst1 = bst1.insert(5);
        bst1 = bst1.insert(17);
        bst1 = bst1.insert(12);
        bst1 = bst1.insert(11);
        bst1 = bst1.insert(14);
        bst1 = bst1.insert(13);
        bst1 = bst1.insert(15);
        bst1 = bst1.insert(16);
        TreePrinter.print(bst1);
        BST bst1Copy1 = bst1;
        BST bst1Copy2 = bst1;
        BST bst1Copy3 = bst1;

        bst1 = bst1.delete(10);
        TreePrinter.print(bst1);

        bst1Copy1 = bst1Copy1.delete(4);
        TreePrinter.print(bst1Copy1);

        bst1Copy2 = bst1Copy2.delete(5);
        TreePrinter.print(bst1Copy2);

        bst1Copy3 = bst1Copy3.delete(17);
        TreePrinter.print(bst1Copy3);


    }
}
