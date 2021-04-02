import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private final Board board;

    public BoardPanel(Board board) {
        this.board = board;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Dimension dim = getSize();
        dim.setSize(0.8*dim.getWidth(), 0.8*dim.getWidth());
        int offset = (int) dim.getWidth() / 10;
        board.paint(g2, offset, dim);
    }

    public Dimension getPreferredSize() {
        int tileSize = 60;
        int boardSize = board.getBoardSize();
        int boardDim = Math.min(tileSize * boardSize, 1200 / boardSize * boardSize);
        return new Dimension(boardDim+10, boardDim+10);
    }
}
