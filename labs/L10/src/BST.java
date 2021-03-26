class EmptyBSTE extends Exception {}

abstract class BST implements TreePrinter.PrintableNode {
    abstract boolean isEmpty ();
    abstract int getValue () throws EmptyBSTE;
    abstract BST getLeftTree () throws EmptyBSTE;
    abstract BST getRightTree () throws EmptyBSTE;
    abstract BST insert (int v);
    abstract BST delete (int v);
    abstract Pair<Integer, BST> deleteLeftMostChild() throws EmptyBSTE;

    // Look at BFSlevel.
    // Do not use BFSlevel to print out array; instead, modify the method itself to print out the tree.
    // You have to do more work than just printing out the levels to the tree - try
    // to format the output so that we can tell what values are in the subtrees; it doesn't need to be
    // as pretty as TreePrinter.
    static void BFSPrint (BST t) {
        // TODO extra credit
    }
}

class Empty extends BST {

    boolean isEmpty () { return true; }

    int getValue() throws EmptyBSTE { throw new EmptyBSTE(); }

    BST getLeftTree () throws EmptyBSTE { throw new EmptyBSTE(); }

    BST getRightTree () throws EmptyBSTE { throw new EmptyBSTE(); }

    BST insert (int v) {
        return new BSTNode(v, this, this);
    }

    // deletes int v from tree; if v not in tree, returns tree as is
    BST delete (int v) {
        return new Empty();
    }

    // deletes leftmost child from empty tree... since leaf doesn't exist,
    // what should we do here ?
    Pair<Integer, BST> deleteLeftMostChild() throws EmptyBSTE {
        throw new EmptyBSTE();
    }

    public TreePrinter.PrintableNode getLeft() { return null; }
    public TreePrinter.PrintableNode getRight() { return null; }
    public String getText() { return null; }
}

class BSTNode extends BST {
    private final int value;
    private final BST leftTree;
    private final BST rightTree;

    BSTNode(int value, BST leftTree, BST rightTree) {
        this.value = value;
        this.leftTree = leftTree;
        this.rightTree = rightTree;
    }

    boolean isEmpty () { return false; }

    int getValue() { return value; }

    BST getLeftTree () {
        return leftTree;
    }

    BST getRightTree () { return rightTree; }

    BST insert (int v) {
        if (v < this.value) {
            return new BSTNode(this.value, leftTree.insert(v), rightTree);
        } else {
            return new BSTNode(this.value, leftTree, rightTree.insert(v));
        }
    }

    // deletes int v from tree; if v not in tree, returns tree as is
    // (hint: we know that int v is not in tree after we have traversed what was necessary)
    // if int v is the root, we want to delete it and replace with leftmost leaf in the right subtree
    BST delete (int v) {

        BST newNode;
        if (v == this.value) {
            try {
                // try to replace this with the leftMostChild
                Pair<Integer, BST> replacement = this.rightTree.deleteLeftMostChild();
                newNode = new BSTNode(replacement.getFirst(), this.leftTree, replacement.getSecond());
            } catch (EmptyBSTE emptyBSTE) {
                // if this.right is empty, then we just return the left subtree
                newNode = this.leftTree;
            }
        }
        else if (v < this.value) {
            newNode = new BSTNode(this.value, this.leftTree.delete(v), this.rightTree);
        }
        else {
            newNode = new BSTNode(this.value, this.leftTree, this.rightTree.delete(v));
        }

        return newNode;
    }

    // returns a pair with the value of the leftmost leaf and the right subtree of this leftmost leaf
    // a very helpful visualization is posted on the canvas Lab 10 page.
    Pair<Integer, BST> deleteLeftMostChild() {

        if (this.leftTree instanceof Empty) {
            // Since this doesn't have a left subtree, this will be the node that is swapped
            return new Pair<>(this.value, this.rightTree);
        }
        else {
            try {
                // Get the value of the leftmost leaf
                Pair<Integer, BST> replacement = this.leftTree.deleteLeftMostChild();

                // Copy the value of the leftmost leaf, but delete it from this
                Pair<Integer, BST> newReplacement = new Pair<>(replacement.getFirst(),
                        new BSTNode(this.value, this.leftTree.delete(replacement.getFirst()),
                                this.rightTree));
                return newReplacement;
            } catch (EmptyBSTE emptyBSTE) {
                emptyBSTE.printStackTrace();
            }
        }

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