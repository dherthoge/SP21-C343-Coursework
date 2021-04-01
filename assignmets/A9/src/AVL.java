
//-----------------------------------------------------------------------
// Empty AVL exception
//-----------------------------------------------------------------------

import java.util.Queue;

class EmptyAVLE extends Exception {}

//-----------------------------------------------------------------------
// Abstract AVL class
//-----------------------------------------------------------------------

abstract class AVL<E extends Comparable<E>> implements TreePrinter.PrintableNode {

    //--------------------------
    // Static fields and methods
    //--------------------------
    protected static int Rrotations, Lrotations;

    static void resetRotations () {
        Rrotations = 0;
        Lrotations = 0;
    }
    static int getRRotations() { return Rrotations; }
    static int getLRotations() { return Lrotations; }
    static int getRotations () { return Rrotations + Lrotations; }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    abstract E data() throws EmptyAVLE;
    abstract AVL<E> left() throws EmptyAVLE;
    abstract AVL<E> right() throws EmptyAVLE;
    abstract int height();
    abstract boolean isEmpty ();

    //--------------------------
    // Main methods
    //--------------------------

    abstract boolean find(E key);
    abstract AVL<E> insert(E data);
    abstract AVL<E> delete(E data);
    abstract Pair<E, AVL<E>> extractLeftMost() throws EmptyAVLE;
}

//-----------------------------------------------------------------------

class Empty<E extends Comparable<E>> extends AVL<E> {

    //--------------------------
    // Getters and simple methods
    //--------------------------

    E data() throws EmptyAVLE {
        throw new EmptyAVLE();
    }

    AVL<E> left() throws EmptyAVLE {
        throw new EmptyAVLE();
    }

    AVL<E> right() throws EmptyAVLE {
        throw new EmptyAVLE();
    }

    int height() { return 0; }

    boolean isEmpty () { return true; }

    //--------------------------
    // Main methods
    //--------------------------

    boolean find(E data) {
        return false;
    }

    AVL<E> insert(E data) {
        return new Node<>(data, new Empty<>(), new Empty<>());
    }

    AVL<E> delete(E key) {
        // ignore delete action if key not found
        return this;
    }

    Pair<E, AVL<E>> extractLeftMost() throws EmptyAVLE {
        throw new EmptyAVLE();
    }

    //--------------------------
    // Printable interface
    //--------------------------

    public TreePrinter.PrintableNode getLeft() {
        return null;
    }
    public TreePrinter.PrintableNode getRight() {
        return null;
    }
    public String getText() {
        return "";
    }
}

//-----------------------------------------------------------------------

class Node<E extends Comparable<E>> extends AVL<E> {
    private final E data;
    private final AVL<E> left;
    private final AVL<E> right;
    private final int height;

    Node(E data, AVL<E> left, AVL<E> right) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.height = 1 + Math.max(left.height(), right.height());
    }

    /**
     * This method is essentially a constructor that guarantees
     * the result is balanced. In more detail:
     *   - if the heights of the left and right subtrees are
     *     no more than 1 apart then a new Node is constructed
     *     and returned; this node is balanced
     *   - if the height of the left subtree is larger than the
     *     height of the right subtree by 2 or more or vice versa
     *     then the constructed node will not be balanced. To
     *     balance it, a possible rotation is performed on either
     *     the left or right subtree, and then the constructed
     *     node is rotated in the appropriate direction.
     */
    Node<E> balance (E data, AVL<E> left, AVL<E> right) {

        // too heavy on the left, rotate right
        if (left.height() - right.height() > 1) {

            // If the left is empty, there is no bias
            if (left instanceof Empty) return new Node<>(data, left, right).rotateRight();

            // to make sure the left is not right biased, make sure left is an instance of Node
            // and then call possibleRotateLeft on it
            Node<E> nodeLeft = ((Node<E>) left).possibleRotateLeft();
            return new Node<>(data, nodeLeft, right).rotateRight();

        }
        // too heavy on the right, rotate left
        else if (right.height() - left.height() > 1) {

            // Symmetric case of above
            if (right instanceof Empty) return new Node<>(data, left, right).rotateLeft();
            Node<E> nodeRight = ((Node<E>) right).possibleRotateRight();
            return new Node<>(data, left, nodeRight).rotateLeft();
        }
        // tree is balanced
        else return new Node<>(data, left, right);
    }

    /**
     * If the current tree is unbalanced with the height of the left subtree
     * smaller than the height of the right subtree, then rotate left;
     * otherwise do nothing
     */
    Node<E> possibleRotateLeft () {

        if (this.right.height() > this.left.height()) return this.rotateLeft();
        else return this;
    }

    /**
     * If the current tree is unbalanced with the height of the right subtree
     * smaller than the height of the left subtree, then rotate right;
     * otherwise do nothing
     */
    Node<E> possibleRotateRight () {
        if (this.left.height() > this.right.height()) return this.rotateRight();
        else return this;
    }

    /**
     * This method should never be called if the left subtree is empty.
     * The method returns a new tree that is right-rotated as follows:
     *              x                          y
     *            /   \                      /   \
     *           y     C     ===>           A     x
     *         /   \                             /  \
     *        A    B                            B    C
     *
     * For testing purposes, we globally count the number of rotations
     * by incrementing Rrotations.
     */
    Node<E> rotateRight() {
        assert !left.isEmpty();
        Rrotations++;

        AVL<E> balancedAVL = new Empty();
        try {
            balancedAVL = new Node(this.left.data(), this.left.left(),
                    new Node<>(this.data, this.left.right(), this.right));
        } catch (EmptyAVLE e) {}

        return (Node<E>) balancedAVL;
    }

    /**
     * This is the symmetric case of rotateRight
     */
    Node<E> rotateLeft() {
        assert !right.isEmpty();
        Lrotations++;

        AVL<E> balancedAVL = new Empty();
        try {
            balancedAVL = new Node(this.right.data(), new Node<>(this.data,
                    this.left, this.right.left()), this.right.right());
        } catch (EmptyAVLE e) {}

        return (Node<E>) balancedAVL;
    }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    E data() { return data; }
    AVL<E> left() { return left; }
    AVL<E> right() { return right; }
    int height() { return height; }
    boolean isEmpty () { return false; }

    //--------------------------
    // Main methods
    //--------------------------

    /**
     * If the given key is in the tree, it will either be
     * equal to the current 'data' or found in the left
     * subtree if it is smaller than the current data, or
     * in the right subtree if it is greater than the
     * current data.
     */
    boolean find(E key) {
        if (key.compareTo(this.data) == 0) return true;
        else if (key.compareTo(this.data) < 0) return this.left.find(key);
        else return this.right.find(key);
    }

    /**
     * If the key is smaller than or equal to the current data
     * insert it to the left; otherwise insert it to the right
     * Of course the insertion might cause the tree to become
     * unbalanced and a corrective action must be taken to
     * re-balance it.
     */
    AVL<E> insert(E key) {

        if (key.compareTo(this.data) <= 0) return balance(this.data, this.left.insert(key),
                this.right);
        else return balance(this.data, this.left, this.right.insert(key));
    }

    /**
     * The key is first compared to the current data; if it
     * smaller then we recursively delete from the left
     * subtree and re-balance. If it is bigger we recursively
     * delete from the right subtree and re-balance.
     *
     * If however it is equal to the current data, it means
     * we need to replace the root of this tree by other
     * data. To maintain order, we will find the smallest
     * value in the right subtree and store in the current
     * node.
     */
    AVL<E> delete(E key) {

        AVL<E> treeWithoutKey;
        if (key.compareTo(this.data) < 0) {
            // If this is larger than the given key
            treeWithoutKey = balance(this.data, this.left.delete(key), this.right);
        }
        else if (key.compareTo(this.data) > 0) {
            treeWithoutKey = balance(this.data, this.left, this.right.delete(key));
        }
        else {
            try {
                Pair<E, AVL<E>> replacement = this.right.extractLeftMost();
                // try to replace this with the leftMostChild
                treeWithoutKey = balance(replacement.getFirst(), this.left,
                        replacement.getSecond());
                TreePrinter.print(treeWithoutKey);
            } catch (EmptyAVLE e) {
                // if this.right is empty, then we just return the left subtree
                treeWithoutKey = this.left;
            }
        }

        return treeWithoutKey;
    }

    /**
     * This is what this method does pictorially:
     *
     *         a                 pair.first = c
     *       /   \               pair.second =
     *      b     C                    a
     *    /   \         ==>           /  \
     *   c     B                     b    C
     *    \                        /  \
     *     A                      A    B
     *
     * The leftmost node 'c' was returned as the first
     * component of the result. The second component
     * was the remaining tree without 'c'. Note however
     * that if removing 'c' were to make the tree
     * unbalanced, it would be balanced before returning it.
     */
    Pair<E, AVL<E>> extractLeftMost() {
        try {
            Pair<E, AVL<E>> recursiveCall = this.left.extractLeftMost();
            return new Pair<>(recursiveCall.getFirst(), balance(this.data,
                    recursiveCall.getSecond(), this.right));
        } catch (EmptyAVLE e) {
            return new Pair<>(this.data, right);
        }
    }

    //--------------------------
    // Printable interface
    //--------------------------

    public TreePrinter.PrintableNode getLeft() {
        return left.isEmpty() ? null : left;
    }
    public TreePrinter.PrintableNode getRight() {
        return right.isEmpty() ? null : right;
    }
    public String getText() {
        return String.valueOf(data);
    }
}

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
