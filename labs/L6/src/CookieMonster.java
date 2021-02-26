import java.util.Arrays;

public class CookieMonster {
    private int[][] cookies, cache;
    private int size;

    CookieMonster (int[][] cookies, int size) {
        this.cookies = cookies;
        this.cache = new int[size][size];
        this.size = size;
    }

    // Given two ints (that represent row and column indices), this method will return
    // the greatest int between the values at (r+1, c), (r+1, c-1), and (r+1, c+1).
    int maxBottom3 (int r, int c) {

        int left = 0;
        try {
            left = cookies[r+1][c-1];
        }catch (ArrayIndexOutOfBoundsException e) {}

        int middle = 0;
        try {
            middle = cookies[r+1][c];
        }catch (ArrayIndexOutOfBoundsException e) {}

        int right = 0;
        try {
            right = cookies[r+1][c+1];
        }catch (ArrayIndexOutOfBoundsException e) {}

        return Math.max(Math.max(left, middle), right);
    }

    int cacheMaxBottom3 (int r, int c) {

        int left = 0;
        try {
            left = cache[r+1][c-1];
        }catch (ArrayIndexOutOfBoundsException e) {}

        int middle = 0;
        try {
            middle = cache[r+1][c];
        }catch (ArrayIndexOutOfBoundsException e) {}

        int right = 0;
        try {
            right = cache[r+1][c+1];
        }catch (ArrayIndexOutOfBoundsException e) {}

        return Math.max(Math.max(left, middle), right);
    }

    // Using a bottom-up (tabular) dynamic programming approach, this method returns the "max amount of
    // cookies".
    // Specifics:
    //      - Your search area will be an NxN matrix. (this.cookies)
    //      - Start from the last row of your matrix and build up.
    //      - Do not change matrix cookies; instead, use this.cache as your tabulation playground.
    //      - Your answer will be in the the first row, middle column (N/2).
    // As always, please read Lab 6's canvas post for more information (in particular, how to build) ^.^
    int findBest () {
        // TODO
        // this will help you debug - will print out the tabulation of your algo :)

//        loop through r = size-2 -> 0
//                loop through c = 0 -> size-1
//                    find max bottom 3 and add current location
//                    store at current location in cache\

        for (int j = 0; j < size; j++) {
            this.cache[size-2][j] = this.cookies[size-2][j] + this.maxBottom3(size-2, j);
        }

        for (int i = size - 3; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                this.cache[i][j] = this.cookies[i][j] + this.cacheMaxBottom3(i, j);
            }
        }

        System.out.println(Arrays.deepToString(cache).replace("], ", "]\n"));

        try {
            return this.cache[0][size/2];
        }catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }
}
