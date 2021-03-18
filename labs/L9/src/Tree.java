class EmptyE extends Exception {}

abstract class Tree implements TreePrinter.PrintableNode {
    abstract boolean isEmpty ();
    abstract int getValue () throws EmptyE;
    abstract Tree getLeftTree () throws EmptyE;
    abstract Tree getRightTree () throws EmptyE;
    abstract Tree insert (int v);
}

class Empty extends Tree {

    boolean isEmpty () { return true; }

    int getValue() throws EmptyE { throw new EmptyE(); }

    Tree getLeftTree () throws EmptyE { throw new EmptyE(); }

    Tree getRightTree () throws EmptyE { throw new EmptyE(); }

    Tree insert (int v) {
        // TODO
        return null;
    }

    public TreePrinter.PrintableNode getLeft() { return null; }
    public TreePrinter.PrintableNode getRight() { return null; }
    public String getText() { return null; }
}

class Node extends Tree {
    private final int value;
    private final Tree leftTree;
    private final Tree rightTree;

    Node (int value, Tree leftTree, Tree rightTree) {
        this.value = value;
        this.leftTree = leftTree;
        this.rightTree = rightTree;
    }

    boolean isEmpty () { return false; }

    int getValue() { return value; }

    Tree getLeftTree () {
        return leftTree;
    }

    Tree getRightTree () { return rightTree; }

    Tree insert (int v) {
        // TODO
        return null;
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
}
