public class Board {

    private int width;
    private int height;
    private int numberOfMines;
    private Square[][] matrix;

    public Board(int width, int height, int numberOfMines) {
        this.width = width;
        this.height = height;
        this.numberOfMines = numberOfMines;
        regenerateBoard();
    }

    private void regenerateBoard() {
        this.matrix = new Square[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = new Square();
            }
        }
    }

    public int getCoveredCount() {
        int covered = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (matrix[i][j].getState() == SquareState.COVERED
                        || matrix[i][j].getState() == SquareState.COVERED_AND_FLAGGED) {
                    covered++;
                }
            }
        }
        return covered;
    }

    public int getUncoveredCount() {
        int uncovered = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (matrix[i][j].getState() == SquareState.UNCOVERED) {
                    uncovered++;
                }
            }
        }
        return uncovered;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }
}
