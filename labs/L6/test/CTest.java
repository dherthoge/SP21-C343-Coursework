import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CTest {

    @Test
    void CMTest() {
        int[][] cookies = {
                { 9, 4, 1, 2, 5},
                { 7, 2, 6, 4, 2},
                { 3, 8, 2, 2, 1},
                { 5, 2, 9, 3, 5},
                { 4, 9, 5, 9, 7},
        };

        int m = new CookieMonster(cookies,5).findBest();

        assertEquals(33, m);
        System.out.printf("m=%d%n%n", m);


        int[][] cookies2 = {
                { 0, 0, 0, 0, 0},
                { 0, 1, 0, 0, 1},
                { 1, 0, 1, 0, 1},
                { 0, 0, 0, 0, 0},
                { 1, 1, 0, 0, 0},
        };

        int m2 = new CookieMonster(cookies2,5).findBest();

        assertEquals(3, m2);
        System.out.printf("m2=%d%n%n", m2);


        int[][] cookies3 = {};

        int m3 = new CookieMonster(cookies3,0).findBest();

        assertEquals(0, m3);
        System.out.printf("m3=%d%n%n", m3);


        int[][] cookies4 = {
                { 9, 4, 1, 2, 5, 8, 3, 7},
                { 4, 9, 5, 9, 7, 8, 3, 1},
                { 3, 8, 2, 2, 1, 1, 9, 2},
                { 5, 2, 9, 9, 3, 2, 6, 3},
                { 4, 9, 5, 9, 7, 8, 3, 1},
                { 5, 9, 3, 3, 5, 2, 6, 3},
                { 9, 4, 9, 3, 2, 1, 3, 7},
                { 5, 2, 9, 9, 7, 8, 3, 1},
        };

        int m4 = new CookieMonster(cookies4,8).findBest();

        assertEquals(61, m4);
        System.out.printf("m4=%d%n%n", m4);

        // TODO more correctness tests
    }

    @Test
    void maxBottom3Tests() {
        int[][] cookies = {
                { 9, 4, 1, 2, 5},
                { 7, 2, 6, 4, 2},
                { 3, 8, 2, 2, 1},
                { 5, 2, 9, 3, 5},
                { 4, 9, 5, 9, 7},
        };

        CookieMonster cm = new CookieMonster(cookies, 5);
        assertEquals(7, cm.maxBottom3(0, 1));
        assertEquals(7, cm.maxBottom3(0, 0));
        assertEquals(9, cm.maxBottom3(3, 2));
        assertEquals(0, cm.maxBottom3(4, 4));
        // TODO more correctness tests
    }
}