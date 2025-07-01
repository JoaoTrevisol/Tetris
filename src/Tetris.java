import javax.swing.*;
import java.awt.*;

public class Tetris extends JPanel {

    private final int ROWS = 20;
    private final int COLS = 10;
    private final int CELL_SIZE = 30;
    private int[][] board = new int[ROWS][COLS];
    private Tetromino currentPiece;
    private Timer timer;

    public Tetris() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        setBackground(Color.BLACK);

        // Criar peça "O"
        int[][] shapeO = {
                {1, 1},
                {1, 1}
        };
        currentPiece = new Tetromino(shapeO, 0, 4);

        // Timer que move a peça para baixo
        timer = new Timer(500, e -> {
            if (canMoveDown(currentPiece)) {
                currentPiece.moveDown();
            } else {
                fixPiece();
                currentPiece = new Tetromino(shapeO, 0, 4);
            }
            repaint();
        });
        timer.start();

        // Controle por teclado
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (currentPiece == null) return;

                switch (key) {
                    case java.awt.event.KeyEvent.VK_LEFT:
                        if (canMove(currentPiece, 0, -1)) currentPiece.moveLeft();
                        break;
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        if (canMove(currentPiece, 0, 1)) currentPiece.moveRight();
                        break;
                    case java.awt.event.KeyEvent.VK_DOWN:
                        if (canMove(currentPiece, 1, 0)) currentPiece.moveDown();
                        break;
                }
                repaint();
            }
        });
    }

    private boolean canMoveDown(Tetromino piece) {
        return canMove(piece, 1, 0);
    }

    private boolean canMove(Tetromino piece, int rowOffset, int colOffset) {
        int[][] shape = piece.getShape();
        int newRow = piece.getRow() + rowOffset;
        int newCol = piece.getCol() + colOffset;

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 1) {
                    int boardRow = newRow + r;
                    int boardCol = newCol + c;

                    if (boardRow < 0 || boardRow >= ROWS || boardCol < 0 || boardCol >= COLS) {
                        return false;
                    }

                    if (board[boardRow][boardCol] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void fixPiece() {
        int[][] shape = currentPiece.getShape();
        int startRow = currentPiece.getRow();
        int startCol = currentPiece.getCol();

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 1) {
                    int row = startRow + r;
                    int col = startCol + c;
                    if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                        board[row][col] = 1;
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Grade
        g.setColor(Color.DARK_GRAY);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Blocos fixos
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] != 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        // Peça atual
        if (currentPiece != null) {
            g.setColor(Color.RED);
            int[][] shape = currentPiece.getShape();
            int startRow = currentPiece.getRow();
            int startCol = currentPiece.getCol();

            for (int r = 0; r < shape.length; r++) {
                for (int c = 0; c < shape[r].length; c++) {
                    if (shape[r][c] == 1) {
                        int x = (startCol + c) * CELL_SIZE;
                        int y = (startRow + r) * CELL_SIZE;
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    }
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
