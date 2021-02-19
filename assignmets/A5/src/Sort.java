import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All sorting algorithms must return a NEW sorted list. Do not
 * modify the incoming list
 */
public class Sort {
    static List<Integer> streamSort(List<Integer> ns) {
        return ns.stream().sorted().collect(Collectors.toList());
    }

    static List<Integer> insertionSort (List<Integer> ns) {

        ArrayList<Integer> copiedList = new ArrayList<>(ns);

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

    static List<Integer> mergeSort (List<Integer> ns) {
        // real work done in PList
        return PList.toList(PList.fromList(ns).mergeSort());
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

    /**
     * Steps:
     * 1. create an array incSequence that calls increment above until the
     *    gap is more than half of the size of the array
     * 2. Start from the largest gap and iterate down the list of gaps
     * 3. For each gap, do an insertion sort for the elements separated
     *    by the given gap
     */
    static List<Integer> shellSort (List<Integer> ns) {

        List<Integer> copiedList = new ArrayList<>(ns);

        List<Integer> incSequence = new ArrayList<>();
        incSequence.add(increment(0));
        int n = 1;
        while (incSequence.get(incSequence.size()-1) < ns.size()/2){

            incSequence.add(increment(n));
            n += 1;
        }

        for (int k = incSequence.size()-1; k >= 0; k--) {
            int gap = incSequence.get(k);

            for (int i = gap; i < copiedList.size(); i += gap) {

                int currentInt = copiedList.get(i);
                int j = i-gap;

                while (j >= 0 && currentInt < copiedList.get(j)) {

                    copiedList.set(j+gap, copiedList.get(j));
                    j -= gap;
                }

                copiedList.set(j+gap, currentInt);
            }
        }

        return copiedList;
    }

    static int getDigit (int n, int d) {
        if (d == 0) return n % 10;
        else return getDigit (n / 10, d-1);
    }

    /**
     * Steps:
     * 1. Create 10 buckets represented as ArrayLists, one for each digit
     * 2. For each digit d (d=0 to len-1) with 0 the least significant digit,
     *    do the following
     * 3. Take a number from the input list, look at digit 'd' in that number,
     *    and add it to the bucket 'd'
     * 4. When you finish processing the list, copy the contents of the
     *    buckets into a temporary list
     * 5. Clear the buckets and repeat for the next 'd'
     */
    static List<Integer> radixSort (List<Integer> ns, int len) {

        List<Integer> copiedList = new ArrayList<>(ns);

        List<ArrayList<Integer>> buckets = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) buckets.add(new ArrayList<Integer>());

        for (int d = 0; d < len; d++) {

            for (int i = 0; i < copiedList.size(); i++) {

                int currentInt = copiedList.get(i);

                int bucket = getDigit(currentInt, d);
                buckets.get(bucket).add(currentInt);
            }

            copiedList.clear();
            for (int i = 0; i < 10; i++) {

                ArrayList<Integer> currentBucket = buckets.get(i);
                for (int j = 0; j < currentBucket.size(); j++) {

                    copiedList.add(currentBucket.get(j));
                }
                buckets.get(i).clear();
            }
        }

        return copiedList; // TODO radixSort
    }

}
