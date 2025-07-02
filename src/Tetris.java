import javax.swing.*;
import java.awt.*;

public class Tetris extends JPanel {

    private final int ROWS = 20;
    private final int COLS = 10;
    private final int CELL_SIZE = 30;
    private int[][] board = new int[ROWS][COLS];
    private Tetromino currentPiece;
    private Timer timer;
    private int score = 0;
    private boolean gameOver = false;

    public Tetris() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE + 150, ROWS * CELL_SIZE));
        setBackground(new Color(30, 30, 30)); // fundo cinza escuro

        currentPiece = TetrominoFactory.createRandomTetromino();

        timer = new Timer(500, e -> {
            if (gameOver) return;

            if (canMove(currentPiece, 1, 0)) {
                currentPiece.moveDown();
            } else {
                fixPiece();
                if (gameOver) {
                    timer.stop();
                    repaint();
                    return;
                }
                currentPiece = TetrominoFactory.createRandomTetromino();
                if (!canMove(currentPiece, 0, 0)) {
                    gameOver = true;
                    timer.stop();
                }
            }
            repaint();
        });
        timer.start();

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (gameOver) return;

                int key = e.getKeyCode();
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
                    case java.awt.event.KeyEvent.VK_UP:
                        rotateCurrentPiece();
                        break;
                }
                repaint();
            }
        });
    }

    private void rotateCurrentPiece() {
        currentPiece.rotate();
        if (!canMove(currentPiece, 0, 0)) {
            currentPiece.rotate(); currentPiece.rotate(); currentPiece.rotate();
        }
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
        Color color = currentPiece.getColor();

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 1) {
                    int row = startRow + r;
                    int col = startCol + c;
                    if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                        board[row][col] = color.getRGB();
                    }
                }
            }
        }

        clearFullLines();
    }

    private void clearFullLines() {
        int linesCleared = 0;

        for (int row = ROWS - 1; row >= 0; row--) {
            boolean full = true;
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == 0) {
                    full = false;
                    break;
                }
            }

            if (full) {
                for (int r = row; r > 0; r--) {
                    System.arraycopy(board[r - 1], 0, board[r], 0, COLS);
                }
                for (int c = 0; c < COLS; c++) {
                    board[0][c] = 0;
                }

                linesCleared++;
                row++;
            }
        }

        score += linesCleared * 100;
    }

    private int getShadowRow() {
        int row = currentPiece.getRow();
        while (canMove(currentPiece, row + 1 - currentPiece.getRow(), 0)) {
            row++;
        }
        return row;
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
                    g.setColor(new Color(board[row][col]));
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        // Sombra da peça
        if (currentPiece != null) {
            int shadowRow = getShadowRow();
            int[][] shape = currentPiece.getShape();
            Color baseColor = currentPiece.getColor();
            Color shadowColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 80);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(shadowColor);

            for (int r = 0; r < shape.length; r++) {
                for (int c = 0; c < shape[r].length; c++) {
                    if (shape[r][c] == 1) {
                        int x = (currentPiece.getCol() + c) * CELL_SIZE;
                        int y = (shadowRow + r) * CELL_SIZE;
                        g2.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
            g2.dispose();

            // Peça atual
            g.setColor(baseColor);
            for (int r = 0; r < shape.length; r++) {
                for (int c = 0; c < shape[r].length; c++) {
                    if (shape[r][c] == 1) {
                        int x = (currentPiece.getCol() + c) * CELL_SIZE;
                        int y = (currentPiece.getRow() + r) * CELL_SIZE;
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }

        // Painel lateral
        int panelX = COLS * CELL_SIZE + 10;
        g.setColor(new Color(50, 50, 50));
        g.fillRect(panelX, 0, 140, ROWS * CELL_SIZE);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score:", panelX + 10, 50);
        g.drawString("" + score, panelX + 10, 80);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("GAME OVER", panelX - 40, ROWS * CELL_SIZE / 2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        frame.add(new Tetris());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
