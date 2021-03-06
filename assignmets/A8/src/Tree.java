import java.util.*;
import java.util.function.Function;

class EmptyE extends Exception {}

@FunctionalInterface
interface TriFunction<A,B,C,R> {
    R apply (A a, B b, C c);
}

abstract class Tree implements TreePrinter.PrintableNode {
    abstract boolean isEmpty();
    abstract int getValue() throws EmptyE;
    abstract Tree getLeftTree() throws EmptyE;
    abstract Tree getRightTree() throws EmptyE;

    /**
     * Returns the height of the tree as calculated by the constructor
     */
    abstract int height();

    /**
     * Flip the left and right subtrees at every level
     */
    abstract Tree mirror();

    /**
     * Always insert 'v' in the left substree but flip the subtrees
     * so that next insertion goes in the other subtree
     */
    abstract Tree insert (int v);

    /**
     * Each node has several paths that reach empty nodes and these paths
     * may have different lengths. This method computes the number of
     * paths of maximum length that start at the given node.
     */
    abstract int numberMaxPaths();

    /**
     * Each node has paths that reach empty nodes. This method returns
     * the maximum sum of values along any path that start at the given
     * node
     */
    abstract int maxSum();

    /**
     * To go from node to another, we can move up from one node to a
     * common ancestor and then move down. The largest such distance
     * is called the diameter of the tree.
     */
    abstract int diameter();

    /**
     * Returns a new tree in which the given function was applied at
     * every node of the current tree
     */
    abstract Tree map (Function<Integer,Integer> f);

    /**
     * At each node, recursively call reduce on the left subtree and the
     * right subtree and then apply the given function to the value at
     * the node and the results of the recursive calls
     */
    abstract int reduce (int base, TriFunction<Integer,Integer,Integer,Integer> f);

    static ArrayList<Integer> DFSRec (Tree t) {
        try {
            ArrayList<Integer> result = new ArrayList<>();
            result.add(t.getValue());
            result.addAll(DFSRec(t.getRightTree()));
            result.addAll(DFSRec(t.getLeftTree()));
            return result;
        }
        catch (EmptyE e) {
            return new ArrayList<>();
        }
    }

    static ArrayList<Integer> DFSIter (Tree t) {
        ArrayList<Integer> result = new ArrayList<>();

        Stack<Tree> stack = new Stack<>();
        stack.push(t);

        while (!stack.isEmpty()) {
            try {
                Tree ct = stack.pop();
                result.add(ct.getValue());
                stack.add(ct.getLeftTree());
                stack.add(ct.getRightTree());
            }
            catch (EmptyE ignored) {}
        }

        return result;
    }

    /**
     * Traverses a tree returning the nodes in breadth first order.
     * The first element in the list is the root, then its two children
     * from left to right, then their four children from left to right, etc.
     */
    static ArrayList<Integer> BFS (Tree t) {
        ArrayList<Integer> result = new ArrayList<>();

        Queue<Tree> queue = new LinkedList<>();
        queue.add(t);

        while (!queue.isEmpty()) {
            try {
                Tree ct = queue.remove();
                result.add(ct.getValue());
                queue.add(ct.getLeftTree());
                queue.add(ct.getRightTree());
            }
            catch (EmptyE ignored) {}
        }

        return result;
    }

    /**
     * Same as BFS except that the values at each level are collected
     * in their own list
     */
    static ArrayList<ArrayList<Integer>> BFSLevel (Tree t)  {

        // Add the first node to the tree
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        Queue<Tree> queue = new LinkedList<>();
        queue.add(t);

        // Check to see if we have nodes left
        while (!queue.isEmpty()) {

            // Count the number of nodes at this level
            int nodesInLine = queue.size();

            // For the nodes in this level
            while (nodesInLine>0) {

                // Remove the current node, add it's data to the result, and add it's childrent
                // to the next level in the queue
                try {
                    Tree ct = queue.remove();
                    result.get(result.size() - 1).add(ct.getValue());
                    queue.add(ct.getLeftTree());
                    queue.add(ct.getRightTree());
                } catch (EmptyE ignored) {}

                // Decrement the number of nodes in the level
                nodesInLine--;
            }

            // The level has no more nodes, create a new ArrayList in result to hold the next line
            result.add(new ArrayList<>());
        }

        // Remove the last two layers of the list since there are no more nodes
        result.remove(result.size()-1);
        result.remove(result.size()-1);

        return result;
    }

    static Tree fromArray (int[] vs) {
        Tree t = new Empty();
        for (int v : vs) t = t.insert(v);
        return t;
    }

    // Debugging methods

    public abstract Tree badInsert(int v);
}

class Empty extends Tree {

    boolean isEmpty() { return true; }

    int getValue() throws EmptyE { throw new EmptyE(); }

    Tree getLeftTree() throws EmptyE { throw new EmptyE(); }

    Tree getRightTree() throws EmptyE { throw new EmptyE(); }

    int height() {
        return 0;
    }

    Tree mirror() {
        return this;
    }

    Tree insert (int v) {
        return new Node(v, new Empty(), new Empty());
    }

    int numberMaxPaths() {
        return 1;
    }

    int maxSum() {
        return 0;
    }

    int diameter() {
        return 0;
    }

    Tree map (Function<Integer,Integer> f) {
        return new Empty();
    }

    int reduce (int base, TriFunction<Integer,Integer,Integer,Integer> f) {
        return base;
    }

    public TreePrinter.PrintableNode getLeft() { return null; }
    public TreePrinter.PrintableNode getRight() { return null; }
    public String getText() { return null; }

    // Debugging methods

    public Tree badInsert(int v) {
        return new Node(v, new Empty(), new Empty());
    }
}

class Node extends Tree {
    private final int value;
    private final Tree leftTree;
    private final Tree rightTree;
    private final int height;

    Node (int value, Tree leftTree, Tree rightTree) {
        this.value = value;
        this.leftTree = leftTree;
        this.rightTree = rightTree;
        this.height = 1 + Math.max(leftTree.height(), rightTree.height());
    }

    boolean isEmpty() { return false; }

    int getValue() { return value; }

    Tree getLeftTree() {
        return leftTree;
    }

    Tree getRightTree() { return rightTree; }

    int height() {
        return this.height;
    }

    Tree mirror() {
        return new Node(this.value, this.rightTree.mirror(), this.leftTree.mirror());
    }

    Tree insert (int v) {
        Tree newTree = new Node(this.value, this.leftTree.insert(v), this.rightTree);

        // Must duplicate the mirror() logic, but we don't want the mirror to run down the tree
        // every time bc mirror is recursive, just want it to mirror the current level
        try {
            newTree = new Node(newTree.getValue(), newTree.getRightTree(), newTree.getLeftTree());
        } catch (EmptyE e) {}
        return newTree;
    }

    int numberMaxPaths() {

        if (this.leftTree.height() == this.rightTree.height()) {
            return this.leftTree.numberMaxPaths() + this.rightTree.numberMaxPaths();
        }
        else if (this.leftTree.height() >= this.rightTree.height()) {
            return this.leftTree.numberMaxPaths();
        }
        else return this.rightTree.numberMaxPaths();
    }

    int maxSum() {

        return Math.max(this.leftTree.maxSum()+this.value, this.rightTree.maxSum()+this.value);
    }

    int diameter() {

        // Calculate the maximum diameter of the children
        int maxSubDiameter = Math.max(this.leftTree.diameter(), this.rightTree.diameter());

        // Calculate the heights of the subtrees combined
        int heightLR = this.leftTree.height()+1 + this.rightTree.height()+1;

        // If the heightLR is >= the maxSubDiameter, then the larges diameter is through the
        // current node
        return Math.max(maxSubDiameter, heightLR);
    }

    Tree map (Function<Integer,Integer> f) {
        return new Node(f.apply(this.value), this.leftTree.map(f), this.rightTree.map(f));
    }

    int reduce (int base, TriFunction<Integer,Integer,Integer,Integer> f) {
        return f.apply(this.value, this.leftTree.reduce(base, f),this.rightTree.reduce(base, f));
    }

    public TreePrinter.PrintableNode getLeft() {
        if (leftTree.isEmpty()) return null;
        else return leftTree;
    }
    public TreePrinter.PrintableNode getRight() {
        if (rightTree.isEmpty()) return null;
        else return rightTree;
    }

    public String getText() { return String.valueOf(value); }

    // Debugging methods

    public Tree badInsert(int v) {
        Tree newTree = new Node(this.value, this.leftTree.badInsert(v), this.rightTree);
        return newTree;
    }
}