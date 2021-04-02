import java.util.ArrayList;
import java.util.Scanner;

class Sudoku_copy {
    private static final int EMPTY = -1;

    private final int dim; // dimension of the entire board
    private final int sdim; // dimension of one of the inner blocks
    private final int[][] cells;
    // counts how much backtracking is
    // done as a simple measure of difficulty
    private int backtracking;

    Sudoku_copy(int[][] cells) {
        this.dim = cells.length;
        this.sdim = (int)Math.sqrt(dim);
        this.cells = cells;
        this.backtracking = 0;
    }

    int getBacktracking () { return backtracking; }

    boolean isBlank (int row, int col) {
        return cells[col][row] == EMPTY;
    }

    // Checks if a value in the given cell location is valid or not
    boolean isValid (int val, int row, int col) {
        return 
            checkRow (val,row) &&
            checkCol (val,col) &&
            checkBlock (val,(row / sdim) * sdim, (col / sdim) * sdim);
    }

    // Checks if a value in a given row is valid
    boolean checkRow (int val, int row) {
        for (int col=0; col<dim; col++) {
            if (cells[col][row] == val) return false;
        }
        return true;
    }

    // Checks if a value in a given column is valid
    boolean checkCol (int val, int col) {
        for (int row=0; row<dim; row++) {
            if (cells[col][row] == val) return false;
        }
        return true;
    }

    // Checks if a value is already in the given block (row and col is the top left cell of the
    // block we want to check)
    boolean checkBlock (int val, int row, int col) {
        for (int j=row; j < row + sdim; j++)
            for (int i=col; i < col + sdim; i++)
                if (cells[i][j] == val) return false;
        return true;
    }

    boolean solve () {

        int[][] cellsCopy = cells;

        int curCol = 0;
        while (curCol != 9) {

            // if we can fill out the current column, move to the next
            if (tryColumn(curCol)) {
                curCol++;
            }
            // if we fail to fill out the current column, move to the previous and reset the
            // current and previous to the their original values
            else {
                curCol--;
                cells[curCol] = cellsCopy[curCol];

                for (int i = 8; i >= 0; i--) {
                    if (cellsCopy[curCol-1][i] == -1) {
                    }
                }
//                int firstBlank = 0;
//                int firstBlankIndex = 0;
//                for (int i = 0; i < 9; i++) {
//
//                    if (cellsCopy[curCol-1][i] == -1) {
//                        firstBlank = cells[curCol-1][i];
//                        firstBlankIndex = i;
//                        break;
//                    }
//                }
//                cells[curCol-1] = cellsCopy[curCol-1];
//                cells[curCol-1][firstBlankIndex] = firstBlank;
//                cells[curCol] = cellsCopy[curCol];
            }
        }


        return false; // TODO
    }

    boolean tryColumn (int col) {

        ArrayList<Integer> cellsAltered = new ArrayList<>();
        boolean backtracked = false;
        for (int r = 0; r < 9; r++) {

            // if the cell is blank or we're backtracking
            if (isBlank(r, col) || backtracked) {
                backtracked = false;
                // try to fill the cell

                // if the cell can be filled, add it to the cells that were altered and move to
                // the next cell
                if (tryCell(col, r)) {
                    cellsAltered.add(r);
                }
                // since the cell cannot be filled, backtrack to the previously altered cell
                else {
                    if (cellsAltered.size() == 0) return false;
                    r = cellsAltered.get(cellsAltered.size()-1)-1;
                    cellsAltered.remove(cellsAltered.size()-1);
                    backtracked = true;
                }
            }
        }


        return true; // TODO
    }

    boolean tryCell (int col, int row) {

        // if the cell is blank, start at 1, else start at the cell's current val+1
        int val = cells[col][row] == -1 ? 1 : cells[col][row]+1;
        for (val = val; val < 10; val++) {

            // if the some value can be placed at the given cell, place it and return true
            if (isValid(val, row, col)) {
                cells[col][row] = val;
                return true;
            }
        }

        // Since no value was valid, return false
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

    public static Sudoku_copy read (Scanner s) {
        // Read in the dimension of the board
        int dim = s.nextInt();
        // Create a new board using dimensions
        int[][] cells = new int[dim][dim];
        // Loop through each col and place either the number or empty into the board
        for (int j = 0; j < dim; j++)
            for (int i = 0; i < dim; i++) {
                String c = s.next();
                cells[i][j] = c.equals(".") ? EMPTY : Integer.parseInt(c);
            }
        // Construct and return a new Sudoku
        return new Sudoku_copy(cells);
    }
}
