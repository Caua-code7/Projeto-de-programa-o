import java.util.Random;

public class Board {
    private final int rows;
    private final int cols;
    private final int totalMines;
    private final Cell[][] cells;
    private int revealedCount;
    private boolean gameOver;
    private boolean win;

    private static class Cell {
        boolean mine;
        boolean revealed;
        boolean flagged;
        int adjacentMines;
    }

    public Board(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        this.cells = new Cell[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cells[r][c] = new Cell();
            }
        }

        placeMines();
        calculateAdjacentMines();
    }

    private void placeMines() {
        Random random = new Random();
        int placed = 0;
        while (placed < totalMines) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);
            if (!cells[r][c].mine) {
                cells[r][c].mine = true;
                placed++;
            }
        }
    }

    private void calculateAdjacentMines() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!cells[r][c].mine) {
                    cells[r][c].adjacentMines = countAdjacentMines(r, c);
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int r = row + dr;
                int c = col + dc;
                if (isValid(r, c) && cells[r][c].mine) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void reveal(int row, int col) {
        if (gameOver || win) return;
        Cell cell = cells[row][col];
        if (cell.flagged || cell.revealed) return;

        if (cell.mine) {
            revealAllMines();
            gameOver = true;
            return;
        }

        floodReveal(row, col);

        if (revealedCount == rows * cols - totalMines) {
            win = true;
        }
    }

    private void floodReveal(int row, int col) {
        if (!isValid(row, col)) return;
        Cell cell = cells[row][col];
        if (cell.revealed || cell.flagged || cell.mine) return;

        cell.revealed = true;
        revealedCount++;

        if (cell.adjacentMines == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        floodReveal(row + dr, col + dc);
                    }
                }
            }
        }
    }

    private void revealAllMines() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (cells[r][c].mine) {
                    cells[r][c].revealed = true;
                }
            }
        }
    }

    public void toggleFlag(int row, int col) {
        Cell cell = cells[row][col];
        if (!cell.revealed) {
            cell.flagged = !cell.flagged;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWin() {
        return win;
    }

    public String render() {
        StringBuilder sb = new StringBuilder();

        sb.append("    ");
        for (int c = 0; c < cols; c++) {
            sb.append(String.format("%3c", (char) ('A' + c)));
        }
        sb.append("\n");

        for (int r = 0; r < rows; r++) {
            sb.append(String.format("%3d ", r + 1));
            for (int c = 0; c < cols; c++) {
                sb.append(String.format("%3c", symbolFor(cells[r][c])));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private char symbolFor(Cell cell) {
        if (cell.flagged) return 'F';
        if (!cell.revealed) return '#';
        if (cell.mine) return '*';
        if (cell.adjacentMines == 0) return '0';
        return Character.forDigit(cell.adjacentMines, 10);
    }
}
