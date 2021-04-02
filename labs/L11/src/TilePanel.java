import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class TilePanel extends JPanel {
    private final Tile tile;
    private final int sizePixels;

    public TilePanel (Tile tile, int sizePixels) {
        this.tile = tile;
        this.sizePixels = sizePixels;
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        Rectangle2D.Double box = new Rectangle2D.Double(50,50,sizePixels,sizePixels);
        tile.paint(g2,box, 3);
    }

    public Dimension getPreferredSize() {
        int size = sizePixels * 4;
        return new Dimension(size,size);
    }
}
