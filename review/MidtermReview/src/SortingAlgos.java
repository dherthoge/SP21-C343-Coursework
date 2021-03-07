import java.util.*;

public class SortingAlgos {

    public static void main(String[] args) {

        Random r = new Random(10);

        // Generate list of unsorted ints
        ArrayList<Integer> unsorted = new ArrayList<>();
        for (int i = 0; i < 100; i++) unsorted.add((int) (r.nextDouble()*1000));

        // Test the algos

        int lastInt;

        ArrayList<Integer> insertionSortSorted = (ArrayList<Integer>) insertionSort(unsorted);
        lastInt = insertionSortSorted.get(0);
        for (int i = 1; i < insertionSortSorted.size(); i++) {
            if (lastInt>insertionSortSorted.get(i)) {
                System.out.println("insertionSort is wrong");
                return;
            }
        }



        ArrayList<Integer> mergeSortSorted = (ArrayList<Integer>) mergeSort(unsorted);
        lastInt = mergeSortSorted.get(0);
        for (int i = 1; i < mergeSortSorted.size(); i++) {
            if (lastInt>mergeSortSorted.get(i)) {
                System.out.println("mergeSort is wrong");
                return;
            }
        }



        ArrayList<Integer> shellSortSorted = (ArrayList<Integer>) shellSort(unsorted);
        lastInt = shellSortSorted.get(0);
        for (int i = 1; i < shellSortSorted.size(); i++) {
            if (lastInt>shellSortSorted.get(i)) {
                System.out.println("shellSort is wrong");
                return;
            }
        }


        ArrayList<Integer> radixSortSorted = (ArrayList<Integer>) radixSort(unsorted, 3);
        lastInt = radixSortSorted.get(0);
        for (int i = 1; i < radixSortSorted.size(); i++) {
            if (lastInt>radixSortSorted.get(i)) {
                System.out.println("radixSort is wrong");
                return;
            }
        }

        System.out.println("All sorting algos are correct");
    }

    public static List<Integer> insertionSort(List<Integer> list) {

        ArrayList<Integer> copiedList = (ArrayList<Integer>) list;


        for (int i = 1; i < copiedList.size(); i++) {

            int currentInt = copiedList.get(i);
            int j = i-1;

            while (j >= 0 && currentInt < copiedList.get(j)) {

                copiedList.set(j+1, copiedList.get(j));
                j--;
            }

            copiedList.set(j+1, currentInt);
        }

        return copiedList;
    }

    public static List<Integer> mergeSort(List<Integer> list) {

        ArrayList<Integer> copiedList = (ArrayList<Integer>) list;

        int middle = copiedList.size() / 2;

        // Make the left and right ArrayLists
        ArrayList<Integer> leftHalf = new ArrayList<>();
        ArrayList<Integer> rightHalf = new ArrayList<>();
        for (int i = 0; i < middle; i++) leftHalf.add(copiedList.get(i));
        for (int i = middle; i < copiedList.size(); i++) rightHalf.add(copiedList.get(i));


        if (leftHalf.size() > 1) leftHalf = (ArrayList<Integer>) mergeSort(leftHalf);
        if (rightHalf.size() > 1) rightHalf = (ArrayList<Integer>) mergeSort(rightHalf);


        return merge(leftHalf, rightHalf);
    }

    public static List<Integer> merge(List<Integer> left, List<Integer> right) {

        Queue<Integer> queueLeft = new LinkedList<>();
        Queue<Integer> queueRight = new LinkedList<>();


        for (int i = 0; i < left.size(); i++) queueLeft.add(left.get(i));
        for (int i = 0; i < right.size(); i++) queueRight.add(right.get(i));


        ArrayList<Integer> mergedList = new ArrayList<>();
        while (!queueLeft.isEmpty() && !queueRight.isEmpty()) {

            if (queueLeft.peek() <= queueRight.peek()) {
                mergedList.add(queueLeft.remove());
            } else {
                mergedList.add(queueRight.remove());
            }
        }

        if (!queueLeft.isEmpty()) for (Integer curInt : queueLeft) mergedList.add(curInt);
        if (!queueRight.isEmpty()) for (Integer curInt : queueRight) mergedList.add(curInt);

        return mergedList;
    }

    static int increment(int n) {
        // From https://oeis.org/search?q=shell+sort
        // a(n) = 9*2^n - 9*2^(n/2) + 1 if n is even;
        // a(n) = 8*2^n - 6*2^((n+1)/2) + 1 if n is odd.
        if (n % 2 == 0)
            return (int) (9 * Math.pow(2, n) - 9 * Math.pow(2, n / 2) + 1);
        else
            return (int) (8 * Math.pow(2,n) - 6 * Math.pow(2,(n + 1) / 2) + 1);
    }

    static List<Integer> findGapSequence(int size) {

        ArrayList<Integer> gapSequence = new ArrayList<>();
        gapSequence.add(increment(0));
        int n = 1;
        int nextGap = increment(n);
        while (nextGap < size/2){

            gapSequence.add(nextGap);
            n += 1;
            nextGap = increment(n);
        }

        return gapSequence;
    }

    public static List<Integer> shellSort(List<Integer> list) {

        ArrayList<Integer> copiedList = (ArrayList<Integer>) list;

        ArrayList<Integer> gapSequence = (ArrayList<Integer>) findGapSequence(list.size());

        for (int k = gapSequence.size()-1; k >= 0; k--) {

            int gap = gapSequence.get(k);

            for (int i = gap; i < copiedList.size(); i++) {

                int curInt = copiedList.get(i);
                int j = i-gap;
                while (j >= 0 && curInt < copiedList.get(j)) {

                    copiedList.set(j+gap, copiedList.get(j));
                    j -= gap;
                }

                copiedList.set(j+gap, curInt);
            }
        }

        return copiedList;
    }

    static int getDigit (int n, int d) {
        if (d == 0) return n % 10;
        else return getDigit (n / 10, d-1);
    }

    public static List<Integer> radixSort(List<Integer> list, int digits) {

        ArrayList<Integer> copiedList = (ArrayList<Integer>) list;

        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < 10; i++) buckets.add(new ArrayList<>());

        for (int digit = 0; digit < digits; digit++) {

            // Add the ints to their buckets
            for (int i = 0; i < copiedList.size(); i++) {

                int curInt = copiedList.get(i);
                buckets.get(getDigit(curInt, digit)).add(curInt);
            }

            // Empty the buckets
            copiedList.clear();
            for (int i = 0; i < 10; i++) {

                ArrayList<Integer> curBucket = buckets.get(i);
                for (int j = 0; j < curBucket.size(); j++) copiedList.add(curBucket.get(j));
                curBucket.clear();
            }
        }

        return copiedList;
    }
}
