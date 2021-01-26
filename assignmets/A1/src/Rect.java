public class Rect {

    private final int xmin;
    private final int ymin;
    private final int xmax;
    private final int ymax;

    Rect(int xmin, int ymin, int xmax, int ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    /**
     * Returns true if the point (x,y) is inside the
     * current rectangle. Touching the sides of the rectangle
     * counts as being inside it.
     */
    boolean contains(int x, int y) {
        return x >= this.xmin && x <= this.xmax &&
                y >= this.ymin && y <= this.ymax;
    }
    

    /**
     * Returns true if the given rectangle r intersects the
     * current one. If the two rectangles touch in even
     * one point, that counts as an intersection.
     */
    boolean intersect(Rect r) {

        boolean rInThis = this.contains(r.xmin, r.ymin)
                || this.contains(r.xmax, r.ymin)
                || this.contains(r.xmin, r.ymax)
                || this.contains(r.xmax, r.ymax);

        boolean thisInR = r.contains(this.xmin, this.ymin)
                || r.contains(this.xmax, this.ymin)
                || r.contains(this.xmin, this.ymax)
                || r.contains(this.xmax, this.ymax);

        return rInThis || thisInR;
    }

    public String toString() {
        return String.format("R[(%d,%d)--(%d,%d)]", this.xmin, this.ymin, this.xmax, this.ymax);
    }
}
