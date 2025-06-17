import javax.swing.*;
import java.awt.*;

public class Tetris extends JPanel {

    private final int ROWS = 20;
    private final int COLS = 10;
    private final int CELL_SIZE = 30;
    private int[][] board = new int[ROWS][COLS];

    public Tetris() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        setBackground(Color.BLACK);

        // TESTE: preencher algumas células
        board[19][4] = 1;
        board[19][5] = 1;
        board[18][4] = 1;
        board[18][5] = 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.DARK_GRAY);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // desenha células ocupadas
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] != 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

        public static void main(String[] args) {
            JFrame frame = new JFrame("Tetris");
            Tetris game = new Tetris();

            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // centraliza a janela
            frame.setVisible(true);
        }
    }


