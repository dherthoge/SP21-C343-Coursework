import java.util.ArrayList;
import java.util.List;

public class Sort {

    // helper function for sort
    // inorder traversal visits left subtree, node, and right subtree respectively
    static List<Integer> inOrder (Tree t) {
        ArrayList<Integer> res = new ArrayList<>();

        try {

            res.addAll(inOrder(t.getLeftTree()));
            res.add(t.getValue());
            res.addAll(inOrder(t.getRightTree()));
        } catch (EmptyE e) {}
        return res;
    }

    // This method should sort the input list by building a BinaryTree
    // Traverse the resulting BinaryTree using inorder traversal to get a sorted ls
    static List<Integer> sort (List<Integer> ls) {

        Tree tree = new Empty();
        for (Integer i : ls ) tree = tree.insert(i);
        return inOrder(tree);
    }
}