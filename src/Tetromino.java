public class Tetromino {
    private int[][] shape;
    private int row;
    private int col;

    public Tetromino(int[][] shape, int startRow, int startCol) {
        this.shape = shape;
        this.row = startRow;
        this.col = startCol;
    }

    public int[][] getShape() {
        return shape;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void moveDown() {
        row++;
    }

    public void moveLeft() {
        col--;
    }

    public void moveRight() {
        col++;
    }
}

