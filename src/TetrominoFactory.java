
import java.awt.Color;
import java.util.Random;

public class TetrominoFactory {
    private static final int[][][] SHAPES = {
            {{1, 1, 1, 1}}, // I
            {{1, 1}, {1, 1}}, // O
            {{0, 1, 0}, {1, 1, 1}}, // T
            {{0, 1, 1}, {1, 1, 0}}, // S
            {{1, 1, 0}, {0, 1, 1}}, // Z
            {{1, 0, 0}, {1, 1, 1}}, // J
            {{0, 0, 1}, {1, 1, 1}}  // L
    };

    private static final Color[] COLORS = {
            Color.CYAN, Color.YELLOW, Color.MAGENTA,
            Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE
    };

    private static final Random rand = new Random();

    public static Tetromino createRandomTetromino() {
        int index = rand.nextInt(SHAPES.length);
        int[][] shape = SHAPES[index];
        Color color = COLORS[index];
        int col = (10 - shape[0].length) / 2;
        return new Tetromino(shape, 0, col, color);
    }
}
