import java.util.HashMap;
import java.util.Map;

class DP {

    // -----------------------------------------------------------------------------
    // Coin changing...
    // -----------------------------------------------------------------------------

    /**
     * Given a list of possible coins that must include a penny,
     * and a number of pennies 'n', in how many ways can we use the
     * coins to make change for 'n'.
     */

    static int coinChange (List<Coin> coins, int n) {
        try {
            if (n < 0) return 0; // no way to make change
            else if (n == 0) return 1; // previous choices succeeded
            else return coinChange(coins.getRest(), n) +
                        coinChange(coins,n - coins.getFirst().getValue());
        }
        catch (EmptyListE e) {
            return 0; // no way to make change
        }
    }

    /**
     * We create a hash table called coinChangeMemo.
     * Each subproblem is determined by the list of coins and by the desired sum.
     * That combination should be the key.
     */
    static final Map<Pair<List<Coin>,Integer>,Integer> coinChangeMemo = new HashMap<>();

    /**
     * Copy the previous solution, sandwich it between hashtable get/put
     */
     static int mcoinChange (List<Coin> coins, int n) {
         Pair<List<Coin>,Integer> probKey = new Pair<>(coins,n);
         if (coinChangeMemo.containsKey(probKey)) return coinChangeMemo.get(probKey);

         int answer;

         try {
             if (n < 0) return 0; // no way to make change
             else if (n == 0) return 1; // previous choices succeeded
             else answer =
                         mcoinChange(coins.getRest(), n) +
                         mcoinChange(coins, n - coins.getFirst().getValue());
         }
         catch (EmptyListE e) {
             answer = 0; // no way to make change
         }

         coinChangeMemo.put(probKey,answer);
         return answer;
     }


    /**
     * We now manage the memoization table manually.
     */
    static int bucoinChange (List<Coin> coins, int n) throws EmptyListE {
        /* The possible lists of coins we will encounter are coins, coins.getRest(),
         * coins.getRest().getRest(), etc. We will refer to these using
         * array indices 0, 1, 2, ...
         * The possible sums we will encounter will be a subset of n, n-1, n-2, ...
         * We will use these directly as indices into the array
         */

         int len = coins.length()+1;
         int[][] table = new int[len][n+1];

         /*
          * The entries corresponding to the empty list are initialized with 0 (no solutions)
          * The entries corresponding to sum=0 are initialized with 1 (one solution)
          * These two initializations must be done in the given order
          */

         for (int i=0; i<n+1; i++) table[len-1][i] = 0;
         for (int c=0; c<len; c++) table[c][0] = 1;

         /*
          * Now we start filling the table. We note from the recursive solution that:
          * the result of coins,n uses coins.getRest(),n and coins,n-value
          * In other words, the value at [c][n] uses entries at [c+1][n] and [c][n-v]
          * So the array must be filled in a particular order starting from high values of c
          * going down to 0 and from low values of i going up to n.
          */


         for (int c=len-2; c>=0; c--) {
             int v = coins.get(c).getValue();
             for (int i = 1; i<n+1; i++) {
                 // make sure we don't go out of bounds
                 if (i - v < 0) table[c][i] = table[c+1][i];
                 else table[c][i] = table[c + 1][i] + table[c][i - v];
             }
         }

         // array is full; return value of original problem
         return table[0][n];
     }

    // -----------------------------------------------------------------------------
    // Partition ...
    // -----------------------------------------------------------------------------

    /**
     * Partition: take a list, a desired sum 's', and return
     * true/false depending on whether it is possible to find a
     * subsequence of the list whose sum is exactly 's'
     */
    static boolean partition (List<Integer> s, int sum) {

        if (sum == 0) return true;
        else {
            try {
                return partition(s.getRest(), sum-s.getFirst()) || partition(s.getRest(), sum);
            }catch (EmptyListE e) {
                return false;
            }
        }
    }

    static final Map<Pair<List<Integer>,Integer>,Boolean> partitionMemo = new HashMap<>();

    static boolean mpartition (List<Integer> s, int sum) {

        // See if this subproblem has already been computed
        Pair<List<Integer>,Integer> probKey = new Pair<>(s, sum);
        if (partitionMemo.containsKey(probKey)) return partitionMemo.get(probKey);

        // Since it hasn't been computed, make a variable to store the answer
        boolean answer;

        // If the sum is zero, then there has to be a correct sequence
        if (sum == 0) return true;
        // Try to break down the subproblem
        else {
            try {
                // See if sum can become 0 by either using the current "coin" or not
                answer = mpartition(s.getRest(), sum-s.getFirst()) || mpartition(s.getRest(), sum);
            }catch (EmptyListE e) {
                answer = false;
            }
        }

        // Store the answer to this subproblem and return it
        partitionMemo.put(probKey, answer);
        return answer;
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

        int penalty = 0;

        try {

            if (dna1.getFirst() == dna2.getFirst()) return MATCH + minDistance(dna1.getRest(),
                    dna2.getRest());
            else {
                // Figures out which of the 3 options has the least overall penalty
                penalty += Math.min(Math.min(MISMATCH + minDistance(dna1.getRest(), dna2.getRest()),
                        GAP + minDistance(dna1.getRest(), dna2)), GAP + minDistance(dna1,
                        dna2.getRest()));
            }
        }catch (EmptyListE e) {
            if (dna1.isEmpty()) return dna2.length() * GAP;
            else return dna1.length() * GAP;
        }

        return penalty;
    }

    static final Map<Pair<List<BASE>,List<BASE>>,Integer> minDistanceMemo = new HashMap<>();

    static int mminDistance (List<BASE> dna1, List<BASE> dna2) {

        Pair<List<BASE>,List<BASE>> probKey = new Pair<>(dna1, dna2);
        if (minDistanceMemo.containsKey(probKey)) return minDistanceMemo.get(probKey);

        int penalty = 0;

        try {

            if (dna1.getFirst() == dna2.getFirst()) return MATCH + mminDistance(dna1.getRest(), dna2.getRest());
            else {
                // Figures out which of the 3 options has the least overall penalty
                penalty += Math.min(Math.min(MISMATCH + mminDistance(dna1.getRest(), dna2.getRest()),
                        GAP + mminDistance(dna1.getRest(), dna2)), GAP + mminDistance(dna1,
                        dna2.getRest()));
            }
        }catch (EmptyListE e) {
            if (dna1.isEmpty()) return dna2.length() * GAP;
            else return dna1.length() * GAP;
        }

        minDistanceMemo.put(probKey,penalty);

        return penalty;
    }

    // -----------------------------------------------------------------------------
    // Longest common subsequence ...
    // -----------------------------------------------------------------------------

    /**
     * We are given two lists of characters. We want the longest
     * subsequence that is common between them. We proceed as follows. We
     * compare the first character of the first list to the first character
     * of the second list and then:
     *   - if they match, include this character in the result computed
     *     recursively
     *   - if they don't match, then try two recursive calls where we
     *     omit the first character from the first list in one call
     *     and omit the first character from the second list in the
     *     second call. Choose the longer of the two results
     */
    static List<Character> lcs (List<Character> cs1, List<Character> cs2) {

        try {
            if (cs1.getFirst().equals(cs2.getFirst())) {
                return new Node<>(cs1.getFirst(), lcs(cs1.getRest(), cs2.getRest()));
            } else {
                List<Character> call1 = lcs(cs1.getRest(), cs2);
                List<Character> call2 = lcs(cs1, cs2.getRest());
                if (call1.length() >= call2.length()) return call1;
                else                                 return call2;
            }
        }catch (EmptyListE e) {
            return new Empty<>();
        }
    }

    static final Map<Pair<List<Character>,List<Character>>,List<Character>> lcsMemo = new HashMap<>();

    static List<Character> mlcs (List<Character> cs1, List<Character> cs2) {

        Pair<List<Character>,List<Character>> probKey = new Pair<>(cs1, cs2);
        if (lcsMemo.containsKey(probKey)) return lcsMemo.get(probKey);

        List<Character> sequence;

        try {
            if (cs1.getFirst().equals(cs2.getFirst())) {
                return new Node<>(cs1.getFirst(), mlcs(cs1.getRest(), cs2.getRest()));
            } else {
                List<Character> call1 = mlcs(cs1.getRest(), cs2);
                List<Character> call2 = mlcs(cs1, cs2.getRest());
                if (call1.length() >= call2.length()) sequence = call1;
                else                                 sequence = call2;
            }
        }catch (EmptyListE e) {
            return new Empty<>();
        }

        lcsMemo.put(probKey,sequence);

        return sequence;
    }
}
