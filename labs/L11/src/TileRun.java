import javax.swing.*;

class TileRun {

    public static void main (String[] args) {
        Tile tile = new Tile('k', 0, 0);
        TilePanel panel = new TilePanel(tile,50);

        JFrame jf = new JFrame("Tile");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(panel);
        jf.pack();
        jf.setVisible(true);
    }
}
