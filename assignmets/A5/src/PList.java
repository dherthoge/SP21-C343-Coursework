import java.util.ArrayList;
import java.util.List;

class PEmptyE extends Exception {}

abstract class PList {
    abstract int getElem () throws PEmptyE;
    abstract PList getRest () throws PEmptyE;
    abstract int length ();

    /**
     * Splits the current list in two at the given
     * index
     */
    abstract Pair<PList,PList> splitAt (int index);

    /**
     * Keep dividing the list in two until you reach
     * a base case; then merge the sorted lists
     * resulting from the recursive calls
     */
    abstract PList mergeSort ();

    /**
     * The given list 'ns' is sorted; the current
     * list (this) is also sorted. Return a new
     * sorted list from these two lists.
     */
    abstract PList merge (PList ns);

    /**
     * Turns a PList into a List.
     */
    static List<Integer> toList (PList ns) {
        List<Integer> result = new ArrayList<>();
        while (true) {
            try {
                result.add(ns.getElem());
                ns = ns.getRest();
            } catch (PEmptyE e) {
                return result;
            }
        }
    }

    /**
     * Turns a List into a PList.
     */
    static PList fromList (List<Integer> ns) {
        PList result = new PEmpty();
        for (int i=0; i<ns.size(); i++) {
            result = new PNode(ns.get(i), result);
        }
        return result;
    }
}

class PEmpty extends PList {
    int getElem() throws PEmptyE {
        throw new PEmptyE();
    }

    PList getRest() throws PEmptyE {
        throw new PEmptyE();
    }

    int length() {
        return 0;
    }

    Pair<PList, PList> splitAt(int index) { return new Pair<>(new PEmpty(), new PEmpty()); }

    PList mergeSort() { return this; }

    PList merge(PList ns) { return ns; }

    @Override
    public String toString() {
        return "PEmpty";
    }
}


class PNode extends PList {
    private final int elem;
    private final PList rest;
    private final int len;

    PNode(int elem, PList rest) {
        this.elem = elem;
        this.rest = rest;
        this.len = rest.length() + 1;
    }

    int getElem() {
        return elem;
    }

    PList getRest() {
        return rest;
    }

    int length() {
        return len;
    }

    Pair<PList, PList> splitAt(int index) {

        PList l1 = splitAtFirstList(index, this);
        PList l2 = splitAtSecondList(index, this);

        int i = 1+1;

        return new Pair<>(l1, l2);
    }

    /**
     * Returns every element PList in the given PList to the given index (exclusive).
     */
    static PList splitAtFirstList(int index, PList pNode)  {

        if (index == 0) {
            return new PEmpty();
        }
        else {
            try {
                return new PNode(pNode.getElem(), splitAtFirstList(index - 1, pNode.getRest()));
            }
            catch (PEmptyE e) { return pNode;}
        }
    }

    /**
     * Returns every element PList in the given PList from the given index (inclusive) on.
     */
    static PList splitAtSecondList(int index, PList pList) {

        // If the index is 0, return the given Plist
        if (index == 0) { return pList; }
        // else decrement the index and recur unless pList is a PEmpty, return the PEmpty
        else {
            try {
                return splitAtSecondList(index - 1, pList.getRest());
            }
            catch (PEmptyE e) {
                return pList;
            }
        }
    }

    PList mergeSort() {

        // Find the middle index of the list
        int middle = this.len / 2;

        // Get the left side of the list
        PList left = this.splitAt(middle).getFst();

        PList sortedLeft;
        // If the left list is greater than 1, keep breaking it in half
        if (left.length() > 1) { sortedLeft = left.mergeSort(); }
        // Else the left list is as small as it can get
        else { sortedLeft = left; }



        // Get the right side of the list
        PList right = this.splitAt(middle).getSnd();

        PList sortedRight;
        // If the right list is greater than 1, keep breaking it in half
        if (right.length() > 1) { sortedRight = right.mergeSort(); }
        // Else the right list is as small as it can get
        else { sortedRight = right; }

        return sortedLeft.merge(sortedRight); // TODO mergeSort
    }

    PList merge(PList ns) {

            try {
                if (this.getElem() < ns.getElem()) {
                    return new PNode(this.getElem(), this.rest.merge(ns));
                }
                else { return new PNode(ns.getElem(), this.merge(ns.getRest())); }
            }
            catch (PEmptyE e) {
                return this;
            }
    }

    @Override
    public String toString() {
        return this.getElem() + ", " + this.getRest().toString();
    }
}

