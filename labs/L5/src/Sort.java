import java.util.ArrayList;
import java.util.List;

public class Sort {

    // This method should sort the input list using the algorithm for insertion sort.
    // That is, first create a new ArrayList with all of the elements from input ns.
    // Then, iterate through this new ArrayList - comparing a current element to its
    // predecessor. While current is less, it is swapped w predecessor.

    // For those who prefer wordier instructions, check out Lab 5 post on canvas :)
    // Otherwise, best of luck on the lab! Tests/debugging will help a lot with IndexOutOfBoundsExceptions
    static List<Integer> insertionSort (List<Integer> ns) {

        ArrayList<Integer> copiedList = new ArrayList<>(ns);

        for (int i = 1; i < copiedList.size(); i++) {

            int currentInt = copiedList.get(i);
            int j = i - 1;

            while (j >= 0 && currentInt < copiedList.get(j)) {

                copiedList.set(j+1, copiedList.get(j));
                j--;
            }

            copiedList.set(j+1, currentInt);
        }

        return copiedList;
    }
}
