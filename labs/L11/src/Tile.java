import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Tile {
    private final char c;
    private final int row, col;
    private boolean visited;

    Tile (char c, int row, int col) {
        this.c = c;
        this.row = row;
        this.col = col;
        reset();
    }

    Tile (int row, int col) {
        Random r = new Random();
        this.c = (char)(r.nextInt(26) + 'a');
        this.row = row;
        this.col = col;
        reset();
    }

    void reset () {
        visited = false;
    }

    boolean isFresh() {
        return !visited;
    }

    void setVisited () {
        this.visited = true;
    }

    int getRow () { return row; }

    int getCol () { return col; }

    public String toString () {
        return String.valueOf(Character.toUpperCase(c));
    }

    public void paint(Graphics2D g2, Rectangle2D.Double r, int scaleFactor) {
        g2.draw(r);
        String s = toString();

        Font font = g2.getFont().deriveFont(16f);
        AffineTransform at = AffineTransform.getScaleInstance(scaleFactor,scaleFactor);
        g2.setFont(font.deriveFont(at));

        FontMetrics fm = g2.getFontMetrics();
        double posx = r.x + ((r.width - fm.stringWidth(s)) / 2);
        double posy = r.y + (((r.height - fm.getHeight()) / 2) + fm.getAscent());
        g2.drawString(s, (int)posx, (int)posy);
    }
}
