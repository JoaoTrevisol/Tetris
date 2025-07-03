
import java.awt.Color;

public class Tetromino {
    private int[][] shape;
    private int row;
    private int col;
    private Color color;

    public Tetromino(int[][] shape, int startRow, int startCol, Color color) {
        this.shape = shape;
        this.row = startRow;
        this.col = startCol;
        this.color = color;
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

    public Color getColor() {
        return color;
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

    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = shape[r][c];
            }
        }
        shape = rotated;
    }
}
