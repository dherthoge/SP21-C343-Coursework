import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RectTest {

    /**
     * This is a very minimal version of the method you need to write.
     * Your test cases should attempt to cover all patterns of
     * intersecting rectangles.
     *
     * We will run your testing method against several implementations
     * of "intersect" that might be incorrect in subtle ways. We expect
     * your test cases to pass when the implementation of "intersect"
     * is correct and to fail when the implementation of "intersect"
     * has errors.
     */
    @Test
    void intersect() {
        Rect r1 = new Rect(0, 0, 10, 10);
        Rect r2 = new Rect(20, 20, 30, 30);
        Rect r3 = new Rect(5, 5, 15, 15);
        Rect r4 = new Rect(10, 10, 15, 15);
        Rect r5 = new Rect(0,0,0,0);
        Rect r6 = new Rect(1,1,1,1);
        Rect r7 = new Rect(-1,-1,1,1);

        assertFalse(r1.intersect(r2));
        assertTrue(r1.intersect(r3));
        assertTrue(r1.intersect(r4));
        assertTrue(r1.intersect(r5));
        assertFalse(r6.intersect(r5));
        assertTrue(r5.intersect(r7));
        assertTrue(r7.intersect(r5));


        // My tests

        // Corners
        Rect r8 = new Rect(10,10,5,5);
        Rect r9 = new Rect(15, 5, 20, 10);
        Rect r10 = new Rect(5,15,10,20);
        Rect r11 = new Rect(15, 15, 20, 20);

        assertTrue(r8.intersect(r4)); // r8 shares bottom right with r4 top left
        assertTrue(r9.intersect(r4)); // r9 shares bottom left with r4 top right
        assertTrue(r10.intersect(r4)); // r10 shares top right with r4 bottom left
        assertTrue(r11.intersect(r4)); // r11 shares top left with r4 bottom right


        // Edges
        Rect r12 = new Rect(-5,0,0,10);
        Rect r13 = new Rect(0,10,10,20);
        Rect r14 = new Rect(10,0,20,10);
        Rect r15 = new Rect(0,-10,10,0);

        assertTrue(r12.intersect(r1)); // r12 shares right with r1 left
        assertTrue(r13.intersect(r1)); // r13 shares top with r1 bottom
        assertTrue(r14.intersect(r1)); // r14 shares left with r1 right
        assertTrue(r15.intersect(r1)); // r13 shares bottom with r1 top


        // Actual overlap
        Rect r16 = new Rect(-5,-5,5,5);
        Rect r17 = new Rect(-5,5,5,15);
        Rect r18 = new Rect(5,5,15,15);
        Rect r19 = new Rect(5,-5,15,5);

        assertTrue(r16.intersect(r1)); // r16 crosses r1 top left corner
        assertTrue(r17.intersect(r1)); // r17 crosses r1 bottom left corner
        assertTrue(r18.intersect(r1)); // r18 crosses r1 bottom right corner
        assertTrue(r19.intersect(r1)); // r19 crosses r1 top right corner


        // Non intersecting rects
        Rect r20 = new Rect(-5,0,-1,10);
        Rect r21 = new Rect(0,11,10,20);
        Rect r22 = new Rect(11,0,20,10);
        Rect r23 = new Rect(0,-10,10,-1);

        assertFalse(r20.intersect(r1)); // r20 almost shares right with r1 left
        assertFalse(r21.intersect(r1)); // r21 almost shares top with r1 bottom
        assertFalse(r22.intersect(r1)); // r22 almost shares left with r1 right
        assertFalse(r23.intersect(r1)); // r23 almost shares bottom with r1 top


        // Rects inside each other?
        Rect r24 = new Rect(0,0,10,10);
        Rect r25 = new Rect(1,1,9,9);

        assertTrue(r1.intersect(r1)); // the same rectangles
        assertTrue(r24.intersect(r25)); // r24 is inside r25
        assertTrue(r25.intersect(r24)); // r25 is overtop r24
    }
}
