import java.util.Scanner;

class Sudoku {
    private static final int EMPTY = -1;

    private final int dim; // dimension of the entire board
    private final int sdim; // dimension of one of the inner blocks
    private final int[][] cells;
    // counts how much backtracking is
    // done as a simple measure of difficulty
    private int backtracking;

    Sudoku (int[][] cells) {
        this.dim = cells.length;
        this.sdim = (int)Math.sqrt(dim);
        this.cells = cells;
        this.backtracking = 0;
    }

    int getBacktracking () { return backtracking; }

    boolean isBlank (int row, int col) {
        return cells[col][row] == EMPTY;
    }

    boolean isValid (int val, int row, int col) {
        return 
            checkRow (val,row) &&
            checkCol (val,col) &&
            checkBlock (val,(row / sdim) * sdim, (col / sdim) * sdim);
    }

    boolean checkRow (int val, int row) {
        for (int col=0; col<dim; col++) {
            if (cells[col][row] == val) return false;
        }
        return true;
    }

    boolean checkCol (int val, int col) {
        for (int row=0; row<dim; row++) {
            if (cells[col][row] == val) return false;
        }
        return true;
    }

    boolean checkBlock (int val, int row, int col) {
        for (int j=row; j < row + sdim; j++)
            for (int i=col; i < col + sdim; i++)
                if (cells[i][j] == val) return false;
        return true;
    }

    boolean solve () {
        return false; // TODO
    }

    boolean tryColumn (int col) {
        return false; // TODO
    }

    boolean tryCell (int col, int row) {
        return false; // TODO
    }
    
    public String toString () {
        StringBuilder res = new StringBuilder();
        for (int j=0; j<dim; j++) {
            if (j % sdim == 0) res.append("――――――――――――――――――――――\n");
            for (int i=0; i<dim; i++) {
                if (i % sdim == 0) res.append("│");
                int c = cells[i][j];
                res.append(c == EMPTY ? "." : c);
                res.append(" ");
            }
            res.append("│\n");
        }
        res.append("――――――――――――――――――――――\n");
        return res.toString();
    }

    //------------------------------------------------------------

    public static Sudoku read (Scanner s) {
        int dim = s.nextInt();
        int[][] cells = new int[dim][dim];
        for (int j = 0; j < dim; j++)
            for (int i = 0; i < dim; i++) {
                String c = s.next();
                cells[i][j] = c.equals(".") ? EMPTY : Integer.parseInt(c);
            }
        return new Sudoku(cells);
    }
}
