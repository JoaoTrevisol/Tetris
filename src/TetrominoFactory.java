import java.awt.Color;
import java.util.Random;

public class TetrominoFactory {
    private static final int[][][] SHAPES = {
            {{1, 1, 1, 1}},       // I
            {{1, 1}, {1, 1}},     // O
            {{0, 1, 0}, {1, 1, 1}}, // T
            {{0, 1, 1}, {1, 1, 0}}, // S
            {{1, 1, 0}, {0, 1, 1}}, // Z
            {{1, 0, 0}, {1, 1, 1}}, // J
            {{0, 0, 1}, {1, 1, 1}}  // L
    };

    private static final Color[] COLORS = {
            new Color(0, 240, 240),   // Cyan I
            new Color(240, 240, 0),   // Yellow O
            new Color(160, 0, 240),   // Purple T
            new Color(0, 240, 0),     // Green S
            new Color(240, 0, 0),     // Red Z
            new Color(0, 0, 240),     // Blue J
            new Color(240, 160, 0)    // Orange L
    };

    private static final Random rand = new Random();

    public static Tetromino createRandomTetromino() {
        int index = rand.nextInt(SHAPES.length);
        int[][] shape = SHAPES[index];
        int col = (10 - shape[0].length) / 2;
        return new Tetromino(shape, 0, col, COLORS[index]);
    }
}
