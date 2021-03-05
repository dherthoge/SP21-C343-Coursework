import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// Bottom-Up Dynamic Programming Practice
public class DP {

    // helper to convert our List class to an ArrayList
    // OR you can use our List class's .get() method
    // converting would be more efficient... can you see why :)
    static <E> ArrayList<E> toArray (List<E> ls) {
        ArrayList<E> converted = new ArrayList<>();
        try {
            converted.add(ls.getFirst());
            converted.addAll(toArray(ls.getRest()));
            return converted;
        } catch (EmptyListE e) {
            return converted;
        }
    }

    // -----------------------------------------------------------------------------
    // Partition ...
    // -----------------------------------------------------------------------------

    /**
     * Partition: take a list, a desired sum 's', and return
     * T/F depending on whether it is possible to find a
     * subsequence of the list whose sum is exactly 's'
     */
    static boolean partition (List<Integer> s, int sum) {
        try {
            return partition(s.getRest(), sum - s.getFirst()) ||
                    partition(s.getRest(), sum);
        }
        catch (EmptyListE e) {
            return sum == 0;
        }
    }

    // hint: use the toArray() method above to convert objects of our List class to an ArrayList
    // do bottom-up approach to partition
    static boolean bupartition (List<Integer> s, int sum) {

        ArrayList<Integer> sArrayList = toArray(s);
        Collections.reverse(sArrayList);

        int width = sArrayList.size()+1;
        int height = sum+1;
        int[][] table = new int[height][width];

        for (int row = 0; row < height; row++) table[row][0] = 0;
        for (int col = 0; col < width; col++) table[0][col] = 1;

        for (int row = 1; row < height; row++) {
            for (int col = 1; col < width; col++) {

                int directMatch = row == sArrayList.get(col-1) ? 1 : 0;
                int restCan = table[row][col-1];
                int someDiag = 0;
                if (row - sArrayList.get(col-1) > 0) someDiag =
                        table[row-sArrayList.get(col-1)][col-1];

                table[row][col] = Math.max(directMatch, Math.max(restCan, someDiag));
            }
        }

        return table[height-1][width-1] == 1 ? true : false;
    }


    // -----------------------------------------------------------------------------
    // Min distance ...
    // -----------------------------------------------------------------------------

    final static int GAP = 2;
    final static int MATCH = 0;
    final static int MISMATCH = 1;

    enum BASE { A, T, G, C }

    /**
     * We want to align two DNA sequences; the idea is to compare
     * the first entry in the first sequence with the first entry in
     * the second sequence:
     * - if they match, then great, move on
     * - if they do not match, we can try to repair this in one of
     *   three possible ways
     *   - pay a penalty of 1 and move on,
     *   - pay a penalty of 2 to use a wild card in the first sequence
     *     and move on
     *   - pay a penalty of 2 to use a wild card in the second sequence
     *     and move on
     * Our goal is to find the best way to align the sequences (with
     * the least penalty)
     */
    static int minDistance (List<BASE> dna1, List<BASE> dna2) {
        try {
            int current = dna1.getFirst() == dna2.getFirst() ? MATCH : MISMATCH;
            int d1 = current + minDistance(dna1.getRest(), dna2.getRest());
            int d2 = GAP + minDistance(dna1.getRest(), dna2);
            int d3 = GAP + minDistance(dna1, dna2.getRest());
            return Math.min(d1,Math.min(d2,d3));
        }
        catch (EmptyListE e) {
            if (dna1.isEmpty()) return GAP * dna2.length();
            else return GAP * dna1.length();
        }
    }


    // feel free to refer to Kev's video (I embedded it again to this week's lab post on Canvas)
    static int buminDistance (List<BASE> dna1, List<BASE> dna2) {

        ArrayList<BASE> dna1ArrList = toArray(dna1);
        ArrayList<BASE> dna2ArrList = toArray(dna2);

        int height = dna1.length()+1;
        int width = dna2.length()+1;
        int[][] table = new int[height][width];

        for (int col = 0; col < width; col++) table[0][col] = col*2;
        for (int row = 0; row < height; row++) table[row][0] = row*2;

        for (int row = 1; row < height; row++) {
            for (int col = 1; col < width; col++) {

                boolean match = dna1ArrList.get(row-1).equals(dna2ArrList.get(col-1));
                int diag = match ? table[row-1][col-1] : table[row-1][col-1]+1;
                int top = table[row-1][col]+2;
                int bottom = table[row][col-1]+2;

                table[row][col] = Math.min(diag, Math.min(top, bottom));
            }
        }

        return table[height-1][width-1];
    }
}
