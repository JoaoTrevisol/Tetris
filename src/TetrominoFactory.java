import java.util.Random;

public class TetrominoFactory {

    private static final int[][][] SHAPES = {
            // I
            {
                    {1, 1, 1, 1}
            },
            // O
            {
                    {1, 1},
                    {1, 1}
            },
            // T
            {
                    {0, 1, 0},
                    {1, 1, 1}
            },
            // S
            {
                    {0, 1, 1},
                    {1, 1, 0}
            },
            // Z
            {
                    {1, 1, 0},
                    {0, 1, 1}
            },
            // J
            {
                    {1, 0, 0},
                    {1, 1, 1}
            },
            // L
            {
                    {0, 0, 1},
                    {1, 1, 1}
            }
    };

    private static final Random rand = new Random();

    public static Tetromino createRandomTetromino() {
        int index = rand.nextInt(SHAPES.length);
        int[][] shape = SHAPES[index];

        // centraliza horizontalmente
        int col = (10 - shape[0].length) / 2;

        return new Tetromino(shape, 0, col);
    }
}
